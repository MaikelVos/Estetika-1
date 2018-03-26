package nl.seventa.estetika.domain;

import java.util.UUID;

/**
 * Created by ywillems on 26-3-2018.
 */

public class CinemaReview extends Review {
    public CinemaReview() {
    }

    public CinemaReview(String reviewId, String author, String text) {
        super(reviewId, author, text);
    }
}
