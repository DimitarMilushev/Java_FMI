package bg.sofia.uni.fmi.mjt.netflix.content;

import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;

import java.util.Objects;
public abstract sealed class VideoContent implements Streamable permits Movie, Series {

    private String name;
    private Genre genre;
    private PgRating rating;
    private int views;

    public VideoContent(String name, Genre genre, PgRating rating)
    {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.views = 0;
    }

    @Override
    public String getTitle() {
        return this.name;
    }

    public abstract int getDuration();

    @Override
    public PgRating getRating() {
        return this.rating;
    }

    @Override
    public Genre getGenre() {
        return this.genre;
    }

    @Override
    public int getViews() {return this.views;}

    @Override
    public void setViews(int num) { this.views = num; }
}
