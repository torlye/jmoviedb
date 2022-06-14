package com.googlecode.jmoviedb.enumerated;

public class AudioTrackType {
    public static final String COMMENTARY_TRACK = "Commentary track";
    public static final String AUDIODESCRIPTIVE_TRACK = "Audio descriptive";

    public static String[] getStringArray() {
        return new String[] {
            "Original language",
            "Dubbed translation",
            "Voice-over translation",
            COMMENTARY_TRACK,
            AUDIODESCRIPTIVE_TRACK,
            "Music only track"
        };
    }
}
