package com.googlecode.jmoviedb.enumerated;

public class SubtitleTrackType {
    public static final String FORCED_SUBS = "Forced subtitles";
    public static final String HEARING_IMPAIRED = "Subtitles for the hearing impaired";

    public static String[] getStringArray() {
        return new String[] {
            FORCED_SUBS,
            HEARING_IMPAIRED,
            AudioTrackType.COMMENTARY_TRACK,
            "Trivia track"
        };
    }
}
