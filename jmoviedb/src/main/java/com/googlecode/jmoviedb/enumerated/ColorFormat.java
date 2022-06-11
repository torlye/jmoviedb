package com.googlecode.jmoviedb.enumerated;

import com.googlecode.jmoviedb.CONST;

public enum ColorFormat {
    none(-1, ""),
    bw(0, "Black & white"),
    color(1, "Colour"),
    hdr10(2, "HDR10"),
    hdr10plus(3, "HDR10+"),
    dolbyvision(4, "Dolby Vision");

    private int id;
    private String name;

    ColorFormat(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

	public boolean isHDR() {
		return id >= 2;
	}

    public static ColorFormat intToEnum(int id) {
		for(ColorFormat c : ColorFormat.values())
			if(id == c.getId())
				return c;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised color format ID: " + id);
		return null;
	}

    public static String[] getAllFormatsStringArray() {
		return getStringArray(ColorFormat.values().length);
	}
    
    public static String[] getSDRFormatsStringArray() {
        return getStringArray(3);
	}

    private static String[] getStringArray(int length) {
        String[] strings = new String[length];
        for(int i = 0; i < length; i++)
            strings[i] = ColorFormat.values()[i].getName();
        return strings;
    }
}
