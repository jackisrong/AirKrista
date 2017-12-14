package airkrista;

public class Flight {

    private String status;
    private String airline;
    private String flightNumber;
    private String destination;
    private String date;
    private String time;
    private int terminal;
    private String plane;
    private double price;
    
    public Flight(String status, String airline, String flightNumber, String destination, String date, String time, int terminal, String plane, double price) {
        this.status = status;
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.terminal = terminal;
        this.plane = plane;
        this.price = price;
    }

    public Flight(String status, String airline, String flightNumber, String destination, String date, String time, int terminal, String plane) {
        this.status = status;
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.terminal = terminal;
        this.plane = plane;
    }
    
    // Add necessary methods
}
