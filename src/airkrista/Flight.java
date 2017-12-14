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
    
    public String getPlane() {
        return this.plane;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public int getSeats() {
        switch (plane) {
            case "B747":
                return 400;
            case "B777":
                return 300;
            case "A310":
                return 250;
            default:
                return 0;
        }
    }
}
