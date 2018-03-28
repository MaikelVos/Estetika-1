package nl.seventa.estetika.domain;

import java.util.UUID;

public abstract class Review {
    private UUID reviewId;
    private String author;
    private String text;

    public Review() {
    }

    public Review(String reviewId, String author, String text) {
        this.reviewId = UUID.fromString(reviewId);
        this.author = author;
        this.text = text;
    }

    public Review(String author, String text) {
        this.reviewId = UUID.randomUUID();
        this.author = author;
        this.text = text;
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = UUID.fromString(reviewId);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
