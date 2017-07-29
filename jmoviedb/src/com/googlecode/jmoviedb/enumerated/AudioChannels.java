package com.googlecode.jmoviedb.enumerated;

import com.googlecode.jmoviedb.CONST;

public enum AudioChannels {
	none(0, ""),
	mono(1, "Mono 1.0"),
	mono2(9, "Mono 2.0"),
	stereo(2, "Stereo 2.0"),
	surround51(3, "5.1"),
	surround61(4, "6.1"),
	surround71(5, "7.1"),
	surround41(6, "4.1"),
	surround50(7, "5.0"),
	surround40(8, "4.0"),
	;
	
	private int id;
	private String description;
	
	private AudioChannels(int id, String desc) {
		this.id = id;
		this.description = desc;
	}

	public int getID() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static AudioChannels intToEnum(int id) {
		for(AudioChannels a : AudioChannels.values())
			if(id == a.getID())
				return a;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised audio channel ID: " + id);
		return null;
	}
	
	public static String[] getStringArray() {
		String[] strings = new String[AudioChannels.values().length];
		for(int i = 0; i < AudioChannels.values().length; i++)
			strings[i] = AudioChannels.values()[i].getDescription();
		return strings;
	}
}
