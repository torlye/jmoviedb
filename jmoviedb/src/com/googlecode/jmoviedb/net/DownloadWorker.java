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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.jmoviedb.CONST;

public class DownloadWorker {
	private URL url;
	private Proxy proxy;

	/**
	 * This constructor takes an URL and tries to detect the correct 
	 * proxy settings for it. The detected proxy will be used 
	 * if this object later tries to connect to the URL.
	 * @param url
	 */
	public DownloadWorker(URL url) {
		if(CONST.DEBUG_MODE && url != null)
			System.out.println("DownloadWorker: " + url.toString());
		this.url = url;
		
		if (System.getProperty("java.net.useSystemProxies") != "true") {
			System.setProperty("java.net.useSystemProxies","true");
		}

		Proxy proxy = null;

		try {
			List<Proxy> l = ProxySelector.getDefault().select(url.toURI());
			
			if (l.size() > 0) {
				proxy = l.get(0);
			}
		} catch (URISyntaxException e) {
			//Let proxy stay null if this fails
		}

		if (proxy == null || proxy.address() == null) {
			if(CONST.DEBUG_MODE)
				System.out.println("No proxy found, trying no proxy");
			this.proxy = Proxy.NO_PROXY;
		} else {
			InetSocketAddress addr = (InetSocketAddress)proxy.address();

			if(CONST.DEBUG_MODE)
				System.out.println("Using proxy "+ proxy.type() 
						+ " " + addr.getHostName() + ":" + addr.getPort());
			this.proxy = proxy;
		}
	}

	/**
	 * Downloads a html file and returns it as one huge string.
	 * @return a String containing html data
	 * @throws IOException
	 */
	public String downloadHtml() throws IOException {
		byte[] bytes = downloadBytes();
		
		return new String (bytes, "UTF-8");
		
	}

	private String getContentCharset(String contentType) {
		if (contentType == null || contentType.length() == 0)
			return null;
		Pattern pattern = Pattern.compile("charset=([A-Za-z0-9\\-\\.:_]+)");
		Matcher matcher = pattern.matcher(contentType);
		if (matcher.find())
			return matcher.group(1);
		return null;
	}

	private URLConnection openConnection() throws IOException {
		URLConnection connection = url.openConnection(proxy);
		
		//Connection to IMDb fails with a 403 if we don't set a User Agent
		connection.setRequestProperty("User-Agent", "None/0.0 (None)");
		connection.setRequestProperty("Accept-Language", "en-US,en");
		connection.setConnectTimeout(CONST.DOWNLOAD_TIMEOUT*1000);
		connection.setReadTimeout(CONST.DOWNLOAD_TIMEOUT*1000);
		return connection;
	}
	
//	public InputStream getHtmlStream() throws IOException {
//		URLConnection connection = openConnection();
//		
//		return connection.getInputStream();
//	}
	
	public byte[] downloadImage() throws IOException {
		byte[] bytes = downloadBytes();
		// Check for valid data
		if(CONST.isValidImage(bytes))
			return bytes;
		if(CONST.DEBUG_MODE)
			System.out.println("Invalid image data");
		return null;
	}

	/**
	 * Downloads any binary file
	 * @return a byte array containing the downloaded data
	 * @throws IOException
	 */
	private byte[] downloadBytes() throws IOException {
		if(url == null)
			return null;

		int tryNumber = 0;

		while(tryNumber<CONST.DOWNLOAD_RETRY_COUNT) {
			tryNumber++;
			if(CONST.DEBUG_MODE)
				System.out.println("Downloading data, attempt #"+ tryNumber);
			
			BufferedInputStream stream = null;
			
			try
			{
				URLConnection connection = openConnection();
				InputStream istream = connection.getInputStream();
				stream = new BufferedInputStream(istream);
				
				ByteArrayOutputStream outbytes = new ByteArrayOutputStream();
				byte[] buf = new byte[4096];
				
				while(true) {
					int len = stream.read(buf); 
					
					if (len <= 0)
						break;
					
					outbytes.write(buf, 0, len);
				}
				
				byte [] byteArray = outbytes.toByteArray();
				
				stream.close();
				if(CONST.DEBUG_MODE)
					System.out.println("Downloaded "+byteArray.length+" bytes");
				
				return byteArray;
			}
			catch (IOException e) {
				continue;
			}
			finally {
				if (stream != null)
					stream.close();
			}
		}
		return null;
	}
}