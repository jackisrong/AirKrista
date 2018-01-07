package airkrista;

import static airkrista.AirKrista.flights;

public class Ticket {

    private String ticketNumber;
    private String name;
    private double price;
    private String time;

    public Ticket(String ticketNumber, String name, double price) {
	this.ticketNumber = ticketNumber;
	this.name = name;
	this.price = price;
    }
    
    public String getTicketNumber() {
        return this.ticketNumber;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public String getTime() {
        String flightNumber = ticketNumber.substring(0, 6);
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNumber().equals(flightNumber)) {
                return flights.get(i).getTime();
            }
        }
        return "0";
    }
}
