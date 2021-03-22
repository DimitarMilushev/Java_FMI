package com.company.song.lyrics;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println(new GetSongLyrics().getLyrics("Eminem", "Beautiful", false));

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
