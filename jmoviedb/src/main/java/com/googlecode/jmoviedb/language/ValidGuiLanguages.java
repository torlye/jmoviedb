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

package com.googlecode.jmoviedb.language;

public enum ValidGuiLanguages {
	english("English", new GuiLanguage()),
	norwegian("Norsk", null);
	
	private String displayName;
	private GuiLanguage langClass; 
	
	public GuiLanguage getLanguageClass() {
		return langClass;
	}

	ValidGuiLanguages(String displayName, GuiLanguage langClass) {
		this.displayName = displayName;
		this.langClass = langClass;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public String toString() {
		return displayName;
	}
	
	public static ValidGuiLanguages StringToEnum(String string) {
		if(string == null) {
			return null;
		}
		for(ValidGuiLanguages l : ValidGuiLanguages.values())
			if(string.toLowerCase().equals(l.toString().toLowerCase()))
				return l;
		
		return null;
	}
}
