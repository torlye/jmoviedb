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

import java.net.*;
import java.io.*;

import com.googlecode.jmoviedb.CONST;

public class HtmlReader {
    URL url;
    String html;
    ImdbParser parser;
	public HtmlReader(URL url) {
		if(CONST.DEBUG_MODE)
			System.out.println(url.toString());
		this.url = url;
		this.html = "";
    }
	
	public ImdbParser download() throws IOException {
		BufferedReader in;
		in = new BufferedReader(new InputStreamReader(url.openStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			html = html+" "+inputLine;
		in.close();
		return new ImdbParser(html);
	}
}