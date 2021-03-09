package com.company.spotify;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record SpotifyTrack(String id, Set<String> artists, String name
                        ,int year, int popularity, double duration
                        ,double tempo, double loudness, double valence
                        ,double acousticness, double danceability, double energy
                        ,double liveness,double speechiness, boolean explicit) {

    //token positions
    private static final int ID = 0;
    private static final int ARTISTS = 1;
    private static final int NAME = 2;
    private static final int YEAR = 3;
    private static final int POPULARITY = 4;
    private static final int DURATION_MS = 5;
    private static final int TEMPO = 6;
    private static final int LOUDNESS = 7;
    private static final int VALENCE = 8;
    private static final int ACOUSTICNESS = 9;
    private static final int DANCEABILITY = 10;
    private static final int ENERGY = 11;
    private static final int LIVENESS = 12;
    private static final int SPEECHINESS = 13;
    private static final int EXPLICIT = 14;
    private static final String IS_EXPLICIT = "1";

    public static SpotifyTrack of(String text) {
        String[] lines = text.split(",");

        String id = lines[ID];
        Set<String> artists = Stream.of(lines[ARTISTS].split(";"))
                            .map(x -> x.replaceAll("\\[", "")
                            .replaceAll("]", "")
                            .replaceAll("'", ""))
                            .collect(Collectors.toSet());

        String name = lines[NAME];
        int year = Integer.parseInt(lines[YEAR]);
        int popularity = Integer.parseInt(lines[POPULARITY]);
        double duration = Double.parseDouble(lines[DURATION_MS]);
        double tempo = Double.parseDouble(lines[TEMPO]);
        double loudness = Double.parseDouble(lines[LOUDNESS]);
        double valence = Double.parseDouble(lines[VALENCE]);
        double acousticness = Double.parseDouble(lines[ACOUSTICNESS]);
        double danceability = Double.parseDouble(lines[DANCEABILITY]);
        double energy = Double.parseDouble(lines[ENERGY]);
        double liveness = Double.parseDouble(lines[LIVENESS]);
        double speechiness = Double.parseDouble(lines[SPEECHINESS]);
        boolean explicit = lines[EXPLICIT].equals(IS_EXPLICIT);

        return new SpotifyTrack(id, artists, name, year, popularity, duration,
                tempo, loudness, valence, acousticness, danceability, energy,
                liveness, speechiness, explicit);
    }
}
