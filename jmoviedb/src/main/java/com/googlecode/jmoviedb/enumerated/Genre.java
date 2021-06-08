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


public enum Genre {
	action(0, "Action", GuiLanguage.GENRE_ACTION, 28), 
	adventure(1, "Adventure", GuiLanguage.GENRE_ADVENTURE, 12),
	animation(2, "Animation", GuiLanguage.GENRE_ANIMATION, 16), 
	biography(3, "Biography", GuiLanguage.GENRE_BIOGRAPHY),
	comedy(4, "Comedy", GuiLanguage.GENRE_COMEDY, 35), 
	crime(5, "Crime", GuiLanguage.GENRE_CRIME, 80), 
	documentary(6, "Documentary", GuiLanguage.GENRE_DOCUMENTARY, 99), 
	drama(7, "Drama", GuiLanguage.GENRE_DRAMA, 18),
	family(8, "Family", GuiLanguage.GENRE_FAMILY, 10751), 
	fantasy(9, "Fantasy", GuiLanguage.GENRE_FANTASY, 14), 
	filmnoir(10, "Film-Noir", GuiLanguage.GENRE_FILMNOIR), 
	gameshow(11, "Game-Show", GuiLanguage.GENRE_GAMESHOW),
	history(12, "History", GuiLanguage.GENRE_HISTORY, 36), 
	horror(13, "Horror", GuiLanguage.GENRE_HORROR, 27), 
	music(14, "Music", GuiLanguage.GENRE_MUSIC, 10402), 
	musical(15, "Musical", GuiLanguage.GENRE_MUSICAL),
	mystery(16, "Mystery", GuiLanguage.GENRE_MYSTERY, 9648), 
	news(17, "News", GuiLanguage.GENRE_NEWS, 10763), 
	realitytv(18, "Reality-TV", GuiLanguage.GENRE_REALITYTV, 10764), 
	romance(19, "Romance", GuiLanguage.GENRE_ROMANCE, 10749),
	scifi(20, "Sci-Fi", GuiLanguage.GENRE_SCIFI, 878), 
	shortmovie(21, "Short", GuiLanguage.GENRE_SHORT), 
	sport(22, "Sport", GuiLanguage.GENRE_SPORT), 
	talkshow(23, "Talk-Show", GuiLanguage.GENRE_TALKSHOW, 10767),
	thriller(24, "Thriller", GuiLanguage.GENRE_THRILLER, 53), 
	war(25, "War", GuiLanguage.GENRE_WAR, 10752), 
	western(26, "Western", GuiLanguage.GENRE_WESTERN, 37),
	actionAdventure(27, "Action & Adventure", GuiLanguage.GENRE_ACTIONADVENTURE, 10759),
	kids(28, "Kids", "Kids", 10762),
	scifiFantasy(29, "Sci-Fi & Fantasy", "Sci-Fi & Fantasy", 10765),
	soap(30, "Soap", "Soap", 10766),
	warPolitics(31, "War & Politics", "War & Politics", 10768);
	
	private int id;
	private Integer tmdbId;
	private String IMDBname;
	private String guiLanguageName;
	
	Genre(int id, String IMDBname, String guiLanguageName) {
		this.id = id;
		this.IMDBname = IMDBname;
		this.guiLanguageName = guiLanguageName;
	}

	Genre(int id, String IMDBname, String guiLanguageName, Integer tmdbID) {
		this(id, IMDBname, guiLanguageName);
		this.tmdbId = tmdbID;
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
	
	public static Genre tmdbGenreToEnum(info.movito.themoviedbapi.model.Genre genre) {
		for(Genre g : Genre.values())
			if(g.tmdbId != null && genre.getId() == g.tmdbId)
				return g;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised genre ID: " + genre.getId());
		return null;
	}
}
