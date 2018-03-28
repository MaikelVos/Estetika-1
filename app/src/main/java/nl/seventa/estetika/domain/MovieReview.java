package nl.seventa.estetika.domain;

public class MovieReview extends Review {
    private Movie movie;

    public MovieReview() {
    }

    public MovieReview(String author, String text, Movie movie) {
        super(author, text);
        this.movie = movie;
    }

    public MovieReview(String author, String text) {
        super(author, text);
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
