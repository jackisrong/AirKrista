package airkrista;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Flight {

    private String status;
    private String airline;
    private String flightNumber;
    private String destination;
    private String date;
    private String time;
    private int terminal;
    private double price;
    private int seats;
    private int ticketsBought = 0;

    public Flight(String status, String airline, String flightNumber, String destination, String date, String time, int terminal, String plane, double price) {
        this.status = status;
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.terminal = terminal;
        this.price = price;
        if (plane.equals("B747")) {
            this.seats = 400;
        } else if (plane.equals("B777")) {
            this.seats = 300;
        } else if (plane.equals("A310")) {
            this.seats = 250;
        }
    }

    public Flight(String status, String airline, String flightNumber, String destination, String date, String time, int terminal, String plane) {
        this.status = status;
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.terminal = terminal;
        if (plane.equals("B747")) {
            this.seats = 400;
        } else if (plane.equals("B777")) {
            this.seats = 300;
        } else if (plane.equals("A310")) {
            this.seats = 250;
        }
    }

    public String getStatus() {
        return this.status;
    }

    public String getAirline() {
        return this.airline;
    }

    public String getFlightNumber() {
        return this.flightNumber;
    }

    public String getDestination() {
        return this.destination;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public int getTerminal() {
        return this.terminal;
    }

    public double getPrice() {
        return this.price;
    }

    public int getSeats() {
        return this.seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
    
    public boolean isDeparture() {
        return this.getStatus().equals("DEP");
    }
    
    public boolean isToday() {
        GregorianCalendar calendar = new GregorianCalendar();
        String currentDate = String.format("%02d", calendar.get(Calendar.DATE)) + "/" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "/" + String.format("%4d", calendar.get(Calendar.YEAR));
        return this.getDate().equals(currentDate);
    }
    
    public void addBoughtTickets(int ticketsBought) {
        this.ticketsBought += ticketsBought;           
    }
    
    public int getBoughtTickets() {
        return this.ticketsBought;
    }
}
