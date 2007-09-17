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

package com.googlecode.jmoviedb.enumerated;

import com.googlecode.jmoviedb.CONST;
import com.googlecode.jmoviedb.language.GuiLanguage;

@SuppressWarnings("static-access")

public enum Genre {
	action(0, "Action", GuiLanguage.GENRE_ACTION), 
	adventure(1, "Adventure", GuiLanguage.GENRE_ADVENTURE),
	animation(2, "Animation", GuiLanguage.GENRE_ANIMATION), 
	biography(3, "Biography", GuiLanguage.GENRE_BIOGRAPHY),
	comedy(4, "Comedy", GuiLanguage.GENRE_COMEDY), 
	crime(5, "Crime", GuiLanguage.GENRE_CRIME), 
	documentary(6, "Documentary", GuiLanguage.GENRE_DOCUMENTARY), 
	drama(7, "Drama", GuiLanguage.GENRE_DRAMA),
	family(8, "Family", GuiLanguage.GENRE_FAMILY), 
	fantasy(9, "Fantasy", GuiLanguage.GENRE_FANTASY), 
	filmnoir(10, "Film-Noir", GuiLanguage.GENRE_FILMNOIR), 
	gameshow(11, "Game-Show", GuiLanguage.GENRE_GAMESHOW),
	history(12, "History", GuiLanguage.GENRE_HISTORY), 
	horror(13, "Horror", GuiLanguage.GENRE_HORROR), 
	music(14, "Music", GuiLanguage.GENRE_MUSIC), 
	musical(15, "Musical", GuiLanguage.GENRE_MUSICAL),
	mystery(16, "Mystery", GuiLanguage.GENRE_MYSTERY), 
	news(17, "News", GuiLanguage.GENRE_NEWS), 
	realitytv(18, "Reality-TV", GuiLanguage.GENRE_REALITYTV), 
	romance(19, "Romance", GuiLanguage.GENRE_ROMANCE),
	scifi(20, "Sci-Fi", GuiLanguage.GENRE_SCIFI), 
	shortmovie(21, "Short", GuiLanguage.GENRE_SHORT), 
	sport(22, "Sport", GuiLanguage.GENRE_SPORT), 
	talkshow(23, "Talk-Show", GuiLanguage.GENRE_TALKSHOW),
	thriller(24, "Thriller", GuiLanguage.GENRE_THRILLER), 
	war(25, "War", GuiLanguage.GENRE_WAR), 
	western(26, "Western", GuiLanguage.GENRE_WESTERN);
	
	private int id;
	private String IMDBname;
	private String guiLanguageName;
	
	Genre(int id, String IMDBname, String guiLanguageName) {
		this.id = id;
		this.IMDBname = IMDBname;
		this.guiLanguageName = guiLanguageName;
	}
	
	public int getID() {
		return id;
	}
	
	public String getIMDBname() {
		return IMDBname;
	}
	
	public String getGuiLanguageName() {
		return guiLanguageName;
	}
	
	public String toString() {
		return IMDBname;
	}
	
	public static Genre StringToEnum(String string) {
		if(string == null) {
			return null;
		}
		for(Genre g : Genre.values())
			if(string.toLowerCase().equals(g.toString().toLowerCase()))
				return g;
		
		return null;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static Genre intToEnum(int id) {
		for(Genre g : Genre.values())
			if(id == g.getID())
				return g;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised genre ID: " + id);
		return null;
	}
}
