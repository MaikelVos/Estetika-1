package nl.seventa.estetika.domain;

import java.util.UUID;

/**
 * Created by ywillems on 26-3-2018.
 */

public class MovieReview extends Review {
    public MovieReview() {
    }

    public MovieReview(String reviewId, String author, String text) {
        super(reviewId, author, text);
    }
}
