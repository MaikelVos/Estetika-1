package nl.seventa.estetika.domain;

/**
 * Created by ywillems on 26-3-2018.
 */

public class Seat {
    private int seatNumber;
    private Hall hall;

    public Seat() {
    }

    public Seat(int seatNumber, Hall hall) {
        this.seatNumber = seatNumber;
        this.hall = hall;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
