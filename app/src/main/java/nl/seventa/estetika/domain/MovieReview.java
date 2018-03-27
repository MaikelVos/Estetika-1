package nl.seventa.estetika.domain;

import java.util.UUID;

/**
 * Created by ywillems on 26-3-2018.
 */

public class MovieReview extends Review {
    private Movie movie;

    public MovieReview() {
    }

    public MovieReview(String reviewId, String author, String text, Movie movie) {
        super(reviewId, author, text);
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
