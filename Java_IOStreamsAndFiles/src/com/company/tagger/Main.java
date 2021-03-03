package com.company.tagger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        final String DATABASE_FILE_PATH = "csv/world-cities.csv";
        final String INPUT_FILE_PATH = "csv/input.csv";
        final String OUTPUT_FILE_PATH = "csv/result.csv";
        final String DELIMITER = ",";


        try (Reader reader = Files.newBufferedReader(Paths.get(DATABASE_FILE_PATH));
            Reader input = Files.newBufferedReader(Paths.get(INPUT_FILE_PATH));
             Writer output = Files.newBufferedWriter(Paths.get(OUTPUT_FILE_PATH))) {

            Tagger tagger = new Tagger(reader);
            tagger.tagCities(input, output);

            System.out.println(tagger.getNMostTaggedCities(10));
            System.out.println(tagger.getAllTaggedCities());
            System.out.println(tagger.getAllTagsCount());
        } catch (IOException e) {
            e.printStackTrace();
        }

       // Tagger tagger = new Tagger();
    }
}
