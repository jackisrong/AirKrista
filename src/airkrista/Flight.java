package airkrista;

// FIX GETTING SEAT NUMBERS -- NOT WORKING


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

    public Flight(String status, String airline, String flightNumber, String destination, String date, String time, int terminal, String plane, double price) {
        this.status = status;
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.terminal = terminal;
        this.price = price;
        switch (plane) {
            case "B747":
                this.seats = 400;
            case "B777":
                this.seats = 300;
            case "A310":
                this.seats = 250;
            default:
                this.seats = 0;
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
        switch (plane) {
            case "B747":
                this.seats = 400;
            case "B777":
                this.seats = 300;
            case "A310":
                this.seats = 250;
            default:
                this.seats = 0;
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
}
