package nl.seventa.estetika.domain;

import java.util.UUID;

public class Ticket {
    private UUID ticketId;
    private Movie movie;
    private Seat assignedSeat;
    private double price;

    public Ticket() {
    }

    public Ticket(UUID ticketId, Movie movie, Seat assignedSeat, double price) {
        this.ticketId = ticketId;
        this.movie = movie;
        this.assignedSeat = assignedSeat;
        this.price = price;
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
