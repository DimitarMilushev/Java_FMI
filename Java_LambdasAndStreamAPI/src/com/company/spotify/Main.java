package com.company.spotify;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        final String TEST_DATA_PATH = "csv/spotify-test-data.csv";

        SpotifyExplorer explorer;

        try (Reader reader = Files.newBufferedReader(Paths.get(TEST_DATA_PATH))) {

            explorer = new SpotifyExplorer(reader);

//            explorer.getExplicitSpotifyTracks()
//                    .forEach(System.out::println);
//            explorer.groupSpotifyTracksByYear()
//                    .forEach((k, v) -> System.out.println(k + ": " + v));

            //System.out.println(explorer.getArtistActiveYears("Rob Zombie"));
            System.out.println(explorer.getNumberOfLongerTracksBeforeYear(10, 1999));

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

