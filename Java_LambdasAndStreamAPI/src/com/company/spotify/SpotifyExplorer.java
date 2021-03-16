package com.company.spotify;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class SpotifyExplorer {

    private Set<SpotifyTrack> tracksSet;
    /**
     * Loads the dataset from the given {@code dataInput} stream.
     *
     * @param dataInput java.io.Reader input stream from which the dataset can be read
     */
    public SpotifyExplorer(Reader dataInput) {

        initializeTrackSet(dataInput);
    }

    private void initializeTrackSet(Reader dataInput) {
        try (BufferedReader reader = new BufferedReader(dataInput)) {

                this.tracksSet = reader.lines().skip(1)
                        .map(SpotifyTrack::of)
                        .collect(Collectors.toSet());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage() + '\n');
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    /**
     * @return all spotify tracks from the dataset as unmodifiable collection
     * If the dataset is empty, return an empty collection
     */
    public Collection<SpotifyTrack> getAllSpotifyTracks() {

        return this.tracksSet.isEmpty() ? Collections.emptySet() : Collections
        .unmodifiableCollection(this.tracksSet);
    }

    /**
     * @return all tracks from the spotify dataset classified as explicit as unmodifiable collection
     * If the dataset is empty or contains no tracks classified as explicit, return an empty collection
     */
    public Collection<SpotifyTrack> getExplicitSpotifyTracks() {
        return this.tracksSet.isEmpty() ? Collections.emptySet() : this.tracksSet.stream()
                .filter(SpotifyTrack::explicit)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Returns all tracks in the dataset, grouped by release year. If no tracks were released in a given year
     * it should not appear as key in the map.
     *
     * @return map with year as a key and the set of spotify tracks released this year as value.
     * If the dataset is empty, return an empty collection
     */
    public Map<Integer, Set<SpotifyTrack>> groupSpotifyTracksByYear() {
        if(this.tracksSet.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Integer, Set<SpotifyTrack>> groupedTracksByYear = new HashMap<>();

        for(SpotifyTrack track : this.tracksSet) {
            if(!groupedTracksByYear.containsKey(track.year())) {
                groupedTracksByYear.put(track.year(), new HashSet<>());
            }

            groupedTracksByYear.get(track.year()).add(track);
        }

        return groupedTracksByYear;
//        groupedTracksByYear = this.tracksSet.stream()
//                .collect(Collectors.groupingBy(SpotifyTrack:year, Collectors.toSet())));
    }

    /**
     * Returns the number of years between the oldest and the newest released tracks of an artist.
     * For example, if the oldest and newest tracks are released in 1996 and 1998 respectively,
     * return 3, if the oldest and newest release match, e.g. 2002-2002, return 1.
     * Note that tracks with multiple authors including the given artist should also be considered in the result.
     *
     * @param artist artist name
     * @return number of active years
     * If the dataset is empty or there are no tracks by the given artist in the dataset, return 0.
     */
    public int getArtistActiveYears(String artist) {
        int yearsGap = 0;

        if(this.tracksSet.isEmpty()) {
            return yearsGap;
        }

        List<Integer> releaseYears = this.tracksSet.stream()
                .filter(track -> track.artists().contains(artist))
                .map(SpotifyTrack::year)
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new));

        if(releaseYears.size() <= 1) {
            return yearsGap;
        }

        yearsGap = getGap(releaseYears.get(0), releaseYears.get(releaseYears.size() - 1));
        return yearsGap;
    }

    private int getGap(Integer latest, Integer earliest) {
        return (earliest - latest) + 1;
        //if gap is 0 return 1, else return actual gap
    }

    /**
     * Returns the @n tracks with highest valence from the 80s.
     * Note that the 80s started in 1980 and lasted until 1989, inclusive.
     * Valence describes the musical positiveness conveyed by a track.
     * Tracks with high valence sound more positive (happy, cheerful, euphoric),
     * while tracks with low valence sound more negative (sad, depressed, angry).
     *
     * @param n number of tracks to return
     *          If @n exceeds the total number of tracks from the 80s, return all tracks available from this period.
     * @return unmodifiable list of tracks sorted by valence in descending order
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public List<SpotifyTrack> getTopNHighestValenceTracksFromThe80s(int n) {
        final int startOfPeriod = 1980;
        final int endOfPeriod = 1989;
        List<SpotifyTrack> tracksFromPeriod = getTracksFromPeriod(startOfPeriod, endOfPeriod);
        tracksFromPeriod.sort((o1, o2) -> Double.compare(o2.valence(), o1.valence()));

        return tracksFromPeriod.stream()
                .limit(n)
                .collect(Collectors.toUnmodifiableList());
    }


    /**
     * Returns the most popular track from the 90s.
     * Note that the 90s started in 1990 and lasted until 1999, inclusive.
     * The value is between 0 and 100, with 100 being the most popular.
     *
     * @return the most popular track of the 90s.
     * If there more than one tracks with equal highest popularity, return any of them
     * @throws NoSuchElementException if there are no tracks from the 90s in the dataset
     */
    public SpotifyTrack getMostPopularTrackFromThe90s() throws NoSuchElementException {
        final int startOfPeriod = 1990;
        final int endOfPeriod = 1999;
        List<SpotifyTrack> tracksFromAge = getTracksFromPeriod(startOfPeriod, endOfPeriod);

        if(tracksFromAge.isEmpty()) {
            throw new NoSuchElementException
                    (String.format("There are tracks from the %s's in our database.", 90));
        }

        return tracksFromAge.stream()
                .reduce((t1, t2) -> t1.popularity() >= t2.popularity() ? t1 : t2)
                .get();
    }
    private List<SpotifyTrack> getTracksFromPeriod(int startOfPeriod, int endOfPeriod) {
        return this.tracksSet.stream()
                .filter(track -> track.year() > startOfPeriod &&
                        track.year() <= endOfPeriod)
                .collect(Collectors.toList());
    }


    /**
     * Returns the number of tracks longer than @minutes released before @year.
     *
     * @param minutes
     * @param year
     * @return the number of tracks longer than @minutes released before @year
     * @throws IllegalArgumentException in case @minutes or @year is a negative number
     */
    public long getNumberOfLongerTracksBeforeYear(int minutes, int year) {
        if(minutes < 0 || year < 0) {
            throw new IllegalArgumentException
                    (String.format("Minutes(%d) and years(%d) cannot be negative numbers!"
                    ,minutes, year));
        }

        return this.tracksSet.stream()
                .filter(track -> track.year() <= year
                && track.duration() > minutes * 60_000)
                .count();
    }

    /**
     * Returns the loudest track released in a given year
     *
     * @param year
     * @return the loudest track released in a given year
     * @throws IllegalArgumentException in case @year is a negative number
     */
    public Optional<SpotifyTrack> getTheLoudestTrackInYear(int year) {
        if(year < 0) {
            throw new IllegalArgumentException(
                    String.format("Year(%d) cannot be a negative number!", year));
        }

        return this.tracksSet.stream()
                .filter(track -> track.year() == year)
                .reduce((t1, t2) -> t1.loudness() >= t2.loudness() ? t1 : t2);
    }
}
