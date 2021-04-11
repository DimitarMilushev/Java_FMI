package com.company.song.lyrics;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetSongLyrics {

    private final String api = "api.lyrics.ovh";
    public String getLyrics(String artist, String songName)
            throws URISyntaxException, IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder().build();

        URI uri = new URI("https",
                this.api,
                "/v1/" + artist + "/" + songName,
                null);

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
