package com.company.song.lyrics;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String artistName = scanner.next();
        String songName = scanner.next();
        try {
            System.out.println(new GetSongLyrics().getLyrics(artistName, songName));

        } catch (URISyntaxException | InterruptedException | IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
