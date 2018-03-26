package nl.seventa.estetika.domain;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ywillems on 26-3-2018.
 */

public class Movie {
    private UUID movieId;
    private String title;
    private String genre;
    private int pegi;
    private String description;
    private String duration;
    private ArrayList<MovieReview> reviews;

    public Movie() {
    }

    public Movie(String movieId, String title, String genre, int pegi, String description, String duration, ArrayList<MovieReview> reviews) {
        this.movieId = UUID.fromString(movieId);
        this.title = title;
        this.genre = genre;
        this.pegi = pegi;
        this.description = description;
        this.duration = duration;
        this.reviews = reviews;
    }

    public UUID getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = UUID.fromString(movieId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPegi() {
        return pegi;
    }

    public void setPegi(int pegi) {
        this.pegi = pegi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ArrayList<MovieReview> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<MovieReview> reviews) {
        this.reviews = reviews;
    }
}
