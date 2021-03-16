package com.company.tagger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.*;


public class Tagger {
    private final String patternBeforeWord = "[^a-zA-Z0-9]\\b";
    private final String patternAfterWord = "\\b[^a-zA-Z0-9]";
    private final String COUNTRY_TAG = "%s<city country=\"%s\">%s</city>%s";

    private final Map<String, ArrayList<String>> countriesList;     //list of Countries with cities
    private final Map<String, Integer> cityOccurrences;             //list of cities and their occurrence count

    /**
     * Creates a new instance of Tagger for a given list of city/country pairs
     *
     * @param citiesReader a java.io.Reader input stream containing list of cities and countries
     *                     in the specified CSV format
     */
    public Tagger(Reader citiesReader) {
        this.countriesList = new HashMap<>();
        this.cityOccurrences = new LinkedHashMap<>();
        this.InitCitiesList(citiesReader);
    }

    private void InitCitiesList(Reader content) {

        try (BufferedReader inputReader = new BufferedReader(content)) {

            String input;
            while ((input = inputReader.readLine()) != null) {
                this.AddPairToCollection(input);
            }
           // this.citiesList = new AbstractMap.SimpleEntry<String, String>());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void AddPairToCollection(String input) {
        String[] tokens = input.split(",");
        String country = tokens[1].toLowerCase();
        String city = tokens[0].toLowerCase();

        if (!this.countriesList.containsKey(country)) {
            this.countriesList.put(country, new ArrayList<>());
        }

        this.countriesList.get(country).add(city);
    }

    private String getNameWithoutSpecialSymbols(String name) {

        return name.replaceAll("'s", "")
                .replaceAll("[^A-Za-z0-9]", "")
                .trim();
    }

    private String getNameWithoutOutsideSymbols(String name) {

        return name.replaceAll(this.patternAfterWord, "")
                .replaceAll(this.patternBeforeWord, "")
                .trim();
    }

    private ArrayList<String> findCountriesInText(ArrayList<String> text) {
        ArrayList<String> citiesList = new ArrayList<>();

        String formattedName ;
        for(String word : text) {
            if(Character.isUpperCase(word.charAt(0))) {
                formattedName = getNameWithoutSpecialSymbols(word);

                if (this.countriesList.containsKey(formattedName)) {
                    citiesList.add(formattedName);
                }
            }
        }

        return citiesList;
    }

    private String getCountryByCity(ArrayList<String> taggedCountries, String word) {
        String name = this.getNameWithoutSpecialSymbols(word);
        String countryName = null;

        for (String taggedCountry : taggedCountries) {
                if (this.countriesList.get(taggedCountry).contains(name)) {

                    countryName = taggedCountry;
                }
            }

            for (String country : this.countriesList.keySet()) {
                if (!taggedCountries.contains(country)) {
                    if(this.countriesList.get(country).contains(name)) {
                        countryName = country;
                    }
                }
            }
            if (countryName != null) {
                countryName = countryName.substring(0, 1).toUpperCase() + countryName.substring(1);
            }

        return countryName;
    }

    private void addCityToList(String name) {
        int count = this.cityOccurrences.getOrDefault(name, 0);
        this.cityOccurrences.put(name, count + 1);
    }
    /**
     * Processes an input stream of a text file, tags any cities and outputs result
     * to a text output stream.
     *
     * @param text   a java.io.Reader input stream containing text to be processed
     * @param output a java.io.Writer output stream containing the result of tagging
     */
    public void tagCities(Reader text, Writer output) {

        try (BufferedReader inputReader = new BufferedReader(text);
             BufferedWriter writer = new BufferedWriter(output)) {

            ArrayList<String> words = new ArrayList<>();

            String input;
            while ((input = inputReader.readLine()) != null) {

                words.addAll(Arrays.asList(input.split(" ")));
            }


            ArrayList<String> taggedCountries = findCountriesInText(words);
            String country;
            for (int i = 0; i < words.size(); i++) {

                country = getCountryByCity(taggedCountries, words.get(i).toLowerCase());
                    if(country == null) {
                        writer.append(words.get(i));
                    }
                    else {
                        this.addCityToList(this.getNameWithoutSpecialSymbols(words.get(i)));
                        /*
                          first %s is the country name
                          second %s is the city name
                         */
                        String prefixSymbol = getBoundarySymbol(words.get(i), true);
                        // true means we get prefix
                        String suffixSymbol = getBoundarySymbol(words.get(i), false);

                        writer.append(
                                String.format(
                                        COUNTRY_TAG, prefixSymbol, country,
                                        this.getNameWithoutOutsideSymbols(words.get(i)), suffixSymbol));
                    }

                    if(i < words.size() - 1) {
                        writer.append(" ");
                    }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    private String getBoundarySymbol(String name, boolean isPrefix) {
        String result = "";

        if(isPrefix) {
            if(name.matches(this.patternBeforeWord)) {
                return name.substring(0, 1);
            }
        }
        else {
            if(name.matches(this.patternAfterWord)) {
                return name.substring(name.length() - 1);
            }
        }

        return "";
    }

    /**
     * Returns a collection the top @n most tagged cities' unique names
     * from the last tagCities() invocation. Note that if a particular city has been tagged
     * more than once in the text, just one occurrence of its name should appear in the result.
     * If @n exceeds the total number of cities tagged, return as many as available
     * If tagCities() has not been invoked at all, return an empty collection.
     *
     * @param n the maximum number of top tagged cities to return
     * @return a collection the top @n most tagged cities' unique names
     * from the last tagCities() invocation.
     */
    public Collection<String> getNMostTaggedCities(int n) {

        if(n > this.cityOccurrences.size()) {
            n = this.cityOccurrences.size();
        }

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        this.cityOccurrences.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        List<String> orderedList = new LinkedList<>(sortedMap.keySet());


        return orderedList.subList(0, n);
    }

    /**
     * Returns a collection of all tagged cities' unique names
     * from the last tagCities() invocation. Note that if a particular city has been tagged
     * more than once in the text, just one occurrence of its name should appear in the result.
     * If tagCities() has not been invoked at all, return an empty collection.
     *
     * @return a collection of all tagged cities' unique names
     * from the last tagCities() invocation.
     */

    public Collection<String> getAllTaggedCities() {
        // TBD: add implementation
        return this.cityOccurrences.keySet();
    }

    /**
     * Returns the total number of tagged cities in the input text
     * from the last tagCities() invocation
     * In case a particular city has been tagged in several occurrences, all must be counted.
     * If tagCities() has not been invoked at all, return 0.
     *
     * @return the total number of tagged cities in the input text
     */
    public long getAllTagsCount() {
        // TBD: add implementation
        long count = 0;
        for (Integer occurrences : this.cityOccurrences.values()) {
            count += occurrences;
        }

        return count;
    }

}
