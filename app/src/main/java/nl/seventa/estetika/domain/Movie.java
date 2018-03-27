package nl.seventa.estetika.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ywillems on 26-3-2018.
 */

public class Movie implements Serializable{
    private int movieId;
    private String title;
    private String genre;
    private String pegi;
    private String description;
    private String duration;
    private String url;
    private ArrayList<MovieReview> reviews;

    public Movie() {
    }

    public Movie(int movieId, String title, String genre, String pegi, String description, String duration, ArrayList<MovieReview> reviews, String url) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.pegi = pegi;
        this.description = description;
        this.duration = duration;
        this.reviews = reviews;
        this.url = url;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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

    public String getPegi() {
        return pegi;
    }

    public void setPegi(String pegi) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<MovieReview> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<MovieReview> reviews) {
        this.reviews = reviews;
    }
}
