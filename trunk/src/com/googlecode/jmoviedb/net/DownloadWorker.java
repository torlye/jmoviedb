/*
 * This file is part of JMoviedb.
 * 
 * Copyright (C) Tor Arne Lye torarnelye@gmail.com
 * 
 * JMoviedb is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMoviedb is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.googlecode.jmoviedb.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import com.googlecode.jmoviedb.CONST;

public class DownloadWorker {
	private URL url;

	public DownloadWorker(URL url) {
		if(CONST.DEBUG_MODE && url != null)
			System.out.println(url.toString());
		this.url = url;
	}

	/**
	 * Downloads a html file and returns it as one huge string.
	 * @return a String containing html data
	 * @throws IOException
	 */
	public String downloadHtml() throws IOException {
		String html = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			html = html+" "+inputLine;
		in.close();
		return html;
	}

	/**
	 * Downloads any binary file
	 * @return a byte array containing the downloaded data
	 * @throws IOException
	 */
	public byte[] downloadBytes() throws IOException {
		if(url == null)
			return null;

		//Read the image into a byte array
		URLConnection connection = url.openConnection();
		int fileSize = connection.getContentLength();
		BufferedInputStream stream = new BufferedInputStream(connection.getInputStream());
		int tryNumber = 0;
		while(tryNumber<CONST.DOWNLOAD_RETRY_COUNT) {
			tryNumber++;
			byte[] imageBytes = new byte[0];
			long startTime = System.currentTimeMillis();

			while(imageBytes.length<fileSize) {

				//Check if timeout has been exceeded
				if(System.currentTimeMillis() > startTime+(CONST.DOWNLOAD_TIMEOUT*1000))
					break;

				//how much data is ready right now?
				int readLength = stream.available();

				//create a new array that contains the bytes read until now, but with free space for more
				byte[] newBytes = new byte[imageBytes.length+readLength];
				System.arraycopy(imageBytes, 0, newBytes, 0, imageBytes.length);

				//read the new bytes into the empty part of the newly created array
				stream.read(newBytes, imageBytes.length, readLength);

				//replace the "old" byte array with the new one
				imageBytes = newBytes;
			}
			stream.close();
			System.out.println("--Downloaded "+imageBytes.length+" bytes");
			// Check for valid data
			if(CONST.isValidImage(imageBytes))
				return imageBytes;
		}
		return null;
	}
}