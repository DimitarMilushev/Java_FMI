package com.company;

import bg.sofia.uni.fmi.mjt.netflix.account.Account;
import bg.sofia.uni.fmi.mjt.netflix.content.Episode;
import bg.sofia.uni.fmi.mjt.netflix.content.Movie;
import bg.sofia.uni.fmi.mjt.netflix.content.Series;
import bg.sofia.uni.fmi.mjt.netflix.content.Streamable;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;
import bg.sofia.uni.fmi.mjt.netflix.platform.Netflix;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Account acc1 = new Account("Stoyan", LocalDateTime.of(2000, 10, 10, 3, 2));
        Account acc2 = new Account("Gosho", LocalDateTime.of(2015, 10, 10, 3, 2));
        Account acc3 = new Account("Tosho", LocalDateTime.of(2005, 10, 10, 3, 2));
        Account acc4 = new Account("Evgeni", LocalDateTime.of(2005, 10, 10, 3, 2));

        Episode ep1 = new Episode("Girl kills for no reason", 40);
        Episode ep2 = new Episode("Something happes idk", 43);
        Episode ep3 = new Episode("Most of them are still alive", 45);

        Episode[] collectionGOT = {ep1, ep2, ep3};

        Movie movie1 = new Movie("Scream", Genre.HORROR, PgRating.NC17, 60);
        Movie movie2 = new Movie("Scary Movie", Genre.COMEDY, PgRating.NC17, 120);
        Movie movie3 = new Movie("Predator", Genre.ACTION, PgRating.PG13, 60);
        Series series = new Series("GOT", Genre.ACTION, PgRating.G, collectionGOT);
        Series series2 = new Series("A good Silent Hill movie", Genre.ACTION, PgRating.G, collectionGOT);

        Account[] accounts = {acc1, acc2, acc3};
        Streamable[] streamables = {movie1, movie2, movie3, series};

        Netflix obj = new Netflix(accounts, streamables);

        obj.watch(acc1, "Scream");

        obj.watch(acc1, "Predator");
        obj.watch(acc1, "Scream");
        obj.watch(acc1, "Scream");
        obj.watch(acc1, "GOT");
        obj.watch(acc3, "Predator");


        /**
        obj.watch(acc1, "Scream");
        obj.watch(acc1, "A good Silent Hill movie"); //missing movie test
        obj.watch(acc4, "Scream"); //not fond exception test
        obj.watch(acc2, "Scream"); //rating test;
         */

        //Streamable mostViewedMovie = obj.mostViewed();
        int totalTimeSpent = obj.totalWatchedTimeByUsers();
        System.out.println(totalTimeSpent + " minutes.");
    }
}
