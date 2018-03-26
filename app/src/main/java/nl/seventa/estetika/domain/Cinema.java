package nl.seventa.estetika.domain;

import java.util.ArrayList;

/**
 * Created by ywillems on 26-3-2018.
 */

public class Cinema {
    private String name = "Estetika";
    private String address = "Lovensdijkstraat 61";
    private String postalCode = "4818AJ";
    private String city = "Breda";
    private ArrayList<CinemaReview> cinemaReviews = new ArrayList<>();
    private ArrayList<Hall> halls = new ArrayList<>();

    public ArrayList<CinemaReview> getCinemaReviews() {
        return cinemaReviews;
    }

    public void setCinemaReviews(ArrayList<CinemaReview> cinemaReviews) {
        this.cinemaReviews = cinemaReviews;
    }

    public ArrayList<Hall> getHalls() {
        return halls;
    }

    public void setHalls(ArrayList<Hall> halls) {
        this.halls = halls;
    }
}
