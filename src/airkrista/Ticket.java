package airkrista;

public class Ticket {

    private String ticketNumber;
    private String name;
    private double price;

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
}
