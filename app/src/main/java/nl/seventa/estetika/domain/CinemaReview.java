package nl.seventa.estetika.domain;

public class CinemaReview extends Review {
    public CinemaReview() {
    }

    public CinemaReview(String reviewId, String author, String text) {
        super(reviewId, author, text);
    }
}
