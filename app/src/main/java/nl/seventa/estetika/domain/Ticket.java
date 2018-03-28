package nl.seventa.estetika.domain;

import java.util.UUID;

/**
 * Created by ywillems on 26-3-2018.
 */

public class Ticket {
    private UUID ticketId;
    private Movie movie;
    private Seat assignedSeat;

    public Ticket() {
    }

    public Ticket(String ticketId, Movie movie, Seat assignedSeat) {
        this.ticketId = UUID.fromString(ticketId);
        this.movie = movie;
        this.assignedSeat = assignedSeat;
    }

    public Ticket(Movie movie, Seat assignedSeat) {
        this.ticketId = UUID.randomUUID();
        this.movie = movie;
        this.assignedSeat = assignedSeat;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = UUID.fromString(ticketId);
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Seat getAssignedSeat() {
        return assignedSeat;
    }

    public void setAssignedSeat(Seat assignedSeat) {
        this.assignedSeat = assignedSeat;
    }
}
