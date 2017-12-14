package airkrista;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AirKrista {

    public static ArrayList<Flight> flights = new ArrayList<Flight>();
    public static ArrayList<Ticket> boughtTickets = new ArrayList<Ticket>();
    public static ArrayList<Ticket> refundedTickets = new ArrayList<Ticket>();
    public static GregorianCalendar calendar = new GregorianCalendar();

    public static void main(String args[]) {
        Scanner keyboard = new Scanner(System.in);
        String input = "0";

        while (true) {
            System.out.println("************ MAIN MENU ************");
            System.out.println("1. Update Database");
            System.out.println("2. Display Arrivals");
            System.out.println("3. Display Departures");
            System.out.println("4. Display Air Canada Flights");
            System.out.println("5. Purchase Tickets");
            System.out.println("6. Refund Tickets");
            System.out.println("7. Logoff");

            input = keyboard.nextLine();

            switch (input) {
                case "1":
                    try {
                        updateDatabase();
                    } catch (IOException e) {
                        System.out.println("IOException");
                    }
                    break;

                case "2":
                    displayArrivals();
                    break;

                case "3":
                    displayDepartures();
                    break;

                case "4":
                    displayAirCanada();
                    break;

                case "5":
                    purchaseTickets();
                    break;

                case "6":
                    refundTickets();
                    break;

                case "7":
                    logoff();
                    break;

                default:
                    System.out.println("Invalid menu entry!");
                    break;
            }
        }
    }

    // Complete the folllowing methods
    public static void updateDatabase() throws IOException {
        Scanner keyboard = new Scanner(System.in);
        BufferedReader inputStream = null;
        String line = null;
        
        // Clear flights and ask for database file name
        flights.clear();
        System.out.println("Choose the file that contains your database");
        String fileName = keyboard.nextLine();
        
        // Read file, get flights and add to ArrayList
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            do {
                line = inputStream.readLine();
                if (line != null) {
                    String[] splitLine = new String[9];
                    StringTokenizer split = new StringTokenizer(line, ",");
                    int numberOfTokens = split.countTokens();
                    for (int i = 0; i < numberOfTokens; i++) {
                        splitLine[i] = split.nextToken();
                    }
                    if (splitLine[0].equals("DEP")) {
                        Flight flight = new Flight(splitLine[0], splitLine[1], splitLine[2], splitLine[3], splitLine[4], splitLine[5], Integer.parseInt(splitLine[6]), splitLine[7], Double.parseDouble(splitLine[8].substring(1)));
                        flights.add(flight);
                    } else {
                        Flight flight = new Flight(splitLine[0], splitLine[1], splitLine[2], splitLine[3], splitLine[4], splitLine[5], Integer.parseInt(splitLine[6]), splitLine[7]);
                        flights.add(flight);
                    }
                }
            } while (line != null);
        } catch (FileNotFoundException e) {
            System.out.println("Error opening file");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static void displayArrivals() {
        String currentDate = String.format("%2d", calendar.get(Calendar.DATE)) + "/" + String.format("%2d", calendar.get(Calendar.MONTH) + 1) + "/" + String.format("%4d", calendar.get(Calendar.YEAR));

        // Print all of todays arrivals
        System.out.println("Arrivals for today are:");
        System.out.println(String.format("%-15s", "Airline") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getStatus().equals("ARR") & flights.get(i).getDate().equals(currentDate)) {
                System.out.println(String.format("%-15s", flights.get(i).getAirline()) + String.format("%-18s", flights.get(i).getFlightNumber()) + String.format("%-18s", flights.get(i).getDestination()) + String.format("%-15s", flights.get(i).getDate()) + String.format("%-10s", flights.get(i).getTime()) + String.format("%-8s", flights.get(i).getTerminal()));
            }
        }
    }

    public static void displayDepartures() {
        String currentDate = String.format("%2d", calendar.get(Calendar.DATE)) + "/" + String.format("%2d", calendar.get(Calendar.MONTH) + 1) + "/" + String.format("%4d", calendar.get(Calendar.YEAR));
        String currentTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));

        // Print all of today's departures except for ones already departed
        System.out.println("Departures for today are:");
        System.out.println(String.format("%-15s", "Airline") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getStatus().equals("DEP") & flights.get(i).getDate().equals(currentDate)) {
                if (Integer.parseInt(flights.get(i).getTime().substring(0, 2)) < Integer.parseInt(currentTime.substring(0, 2))) {
                    continue;
                } else if (Integer.parseInt(flights.get(i).getTime().substring(0, 2)) == Integer.parseInt(currentTime.substring(0, 2))) {
                    if (Integer.parseInt(flights.get(i).getTime().substring(3, 5)) < Integer.parseInt(currentTime.substring(3, 5))) {
                        continue;
                    }
                }
                System.out.println(String.format("%-15s", flights.get(i).getAirline()) + String.format("%-18s", flights.get(i).getFlightNumber()) + String.format("%-18s", flights.get(i).getDestination()) + String.format("%-15s", flights.get(i).getDate()) + String.format("%-10s", flights.get(i).getTime()) + String.format("%-8s", flights.get(i).getTerminal()));
            }
        }
    }

    public static void displayAirCanada() {
        String currentDate = String.format("%2d", calendar.get(Calendar.DATE)) + "/" + String.format("%2d", calendar.get(Calendar.MONTH) + 1) + "/" + String.format("%4d", calendar.get(Calendar.YEAR));
        String currentTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));

        // Print all of today's Air Canada flights except already arrived or departed
        System.out.println("All Air Canada flights for today are:");
        System.out.println(String.format("%-10s", "Status") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getAirline().equals("Air Canada") & flights.get(i).getDate().equals(currentDate)) {
                if (Integer.parseInt(flights.get(i).getTime().substring(0, 2)) < Integer.parseInt(currentTime.substring(0, 2))) {
                    continue;
                } else if (Integer.parseInt(flights.get(i).getTime().substring(0, 2)) == Integer.parseInt(currentTime.substring(0, 2))) {
                    if (Integer.parseInt(flights.get(i).getTime().substring(3, 5)) < Integer.parseInt(currentTime.substring(3, 5))) {
                        continue;
                    }
                }
                System.out.println(String.format("%-10s", flights.get(i).getStatus()) + String.format("%-18s", flights.get(i).getFlightNumber()) + String.format("%-18s", flights.get(i).getDestination()) + String.format("%-15s", flights.get(i).getDestination()) + String.format("%-10s", flights.get(i).getTime()) + String.format("%-8s", flights.get(i).getTerminal()));
            }
        }
    }

    public static void purchaseTickets() {
        String currentDate = String.format("%2d", calendar.get(Calendar.DATE)) + "/" + String.format("%2d", calendar.get(Calendar.MONTH) + 1) + "/" + String.format("%4d", calendar.get(Calendar.YEAR));
        String currentTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));
        Scanner keyboard = new Scanner(System.in);
        ArrayList<Flight> availableFlights = new ArrayList<Flight>();
        Flight chosenFlight = null;
        String numberOfTickets = null;

        // Print all of today's departing Air Canada flights 1 hour or more before flight time
        System.out.println("All departing Air Canada flights for today are:");
        System.out.println(String.format("%-10s", "Choice") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-12s", "Terminal") + String.format("%-15s", "Seats Left") + String.format("%-10s", "Price"));
        int choiceCounter = 0;
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getStatus().equals("DEP") & flights.get(i).getAirline().equals("Air Canada") & flights.get(i).getDate().equals(currentDate)) {
                if (Integer.parseInt(flights.get(i).getTime().substring(0, 2)) < Integer.parseInt(currentTime.substring(0, 2)) + 1) {
                    continue;
                } else if (Integer.parseInt(flights.get(i).getTime().substring(0, 2)) == Integer.parseInt(currentTime.substring(0, 2)) + 1) {
                    if (Integer.parseInt(flights.get(i).getTime().substring(3, 5)) < Integer.parseInt(currentTime.substring(3, 5))) {
                        continue;
                    }
                }
                choiceCounter++;
                availableFlights.add(flights.get(i));
                System.out.println(String.format("%-10s", choiceCounter + ".") + String.format("%-18s", flights.get(i).getFlightNumber()) + String.format("%-18s", flights.get(i).getDestination()) + String.format("%-15s", flights.get(i).getDate()) + String.format("%-10s", flights.get(i).getTime()) + String.format("%-12s", flights.get(i).getTerminal()) + String.format("%-15s", flights.get(i).getSeats()) + String.format("%-10s", "$" + String.format("%.2f", flights.get(i).getPrice())));
            }
        }

        // Ask for flight choice
        while (true) {
            System.out.println("Which flight would you like?");
            String flightChoice = keyboard.nextLine();
            try {
                if (Integer.parseInt(flightChoice) <= choiceCounter & Integer.parseInt(flightChoice) > 0) {
                    chosenFlight = availableFlights.get(Integer.parseInt(flightChoice) - 1);
                    break;
                } else {
                    System.out.println("Sorry, that's an invalid choice.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Sorry, that's an invalid choice.\n");
            }
        }

        // Ask for name
        System.out.println("\nWhat is your name?");
        String ticketName = keyboard.nextLine();

        // Ask for number of tickets
        while (true) {
            System.out.println("\nHow many tickets would you like to purchase?");
            numberOfTickets = keyboard.nextLine();
            try {
                if (Integer.parseInt(numberOfTickets) <= chosenFlight.getSeats() & Integer.parseInt(numberOfTickets) > 0) {
                    break;
                } else {
                    System.out.println("Sorry, that's an invalid choice.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Sorry, that's an invalid choice.\n");
            }
        }

        for (int i = 0; i < Integer.parseInt(numberOfTickets); i++) {
            // CREATE TICKET NUMBERS (MAKE SURE NOT DUPLICATED WITH EXISTING TICKETS)
            Ticket ticket = new Ticket("AC1213:000", ticketName, chosenFlight.getPrice());
            boughtTickets.add(ticket);
        }
        
        // FIX THIS -- CURRENTLY NOT WORKING
        // Update number of seats in flight
        //chosenFlight.setSeats(chosenFlight.getSeats() - Integer.parseInt(numberOfTickets));

        // Print invoice
        System.out.println("\n=========================================================");
        System.out.println("Invoice:\n");
        System.out.println(String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-12s", "Terminal") + String.format("%-15s", "Quantity") + String.format("%-15s", "Price"));
        System.out.println(String.format("%-18s", chosenFlight.getFlightNumber()) + String.format("%-18s", chosenFlight.getDestination()) + String.format("%-15s", chosenFlight.getDate()) + String.format("%-10s", chosenFlight.getTime()) + String.format("%-12s", chosenFlight.getTerminal()) + String.format("%-15s", numberOfTickets) + String.format("%-15s", "$" + String.format("%.2f", Math.round(Integer.parseInt(numberOfTickets) * chosenFlight.getPrice() * 100) / 100.0)));
        System.out.println("\nYour ticket numbers are:");
        for (int i = 0; i < boughtTickets.size(); i++) {
            // PRINT ALL TICKET NUMBERS BOUGHT IN THIS SESSION
        }
        System.out.println("\nThank you for your business, " + ticketName + "!");
        System.out.println("=======================================================");
    }

    public static void refundTickets() {
        Scanner keyboard = new Scanner(System.in);
        Ticket refundedTicket = null;
        boolean foundTicket = false;

        // Ask for valid ticket number
        while (!foundTicket) {
            System.out.println("Please enter a valid ticket number:");
            String ticketNumber = keyboard.nextLine();
            for (int i = 0; i < boughtTickets.size(); i++) {
                if (ticketNumber.equals(boughtTickets.get(i).getTicketNumber())) {
                    refundedTicket = boughtTickets.get(i);
                    boughtTickets.remove(i);
                    refundedTickets.add(refundedTicket);
                    foundTicket = true;
                    break;
                }
            }
            if (!foundTicket) {
                System.out.println("Sorry, invalid ticket number.");
            }
        }

        // Print refund approved
        System.out.println("Your refund has been approved in the amount of $" + refundedTicket.getPrice() + ". Have a nice day " + refundedTicket.getName() + "!");
    }

    public static void logoff() {
        String currentDate = String.format("%2d", calendar.get(Calendar.DATE)) + "/" + String.format("%2d", calendar.get(Calendar.MONTH) + 1) + "/" + String.format("%4d", calendar.get(Calendar.YEAR));
        double totalSales = 0.00;
        double totalRefunds = 0.00;

        System.out.println("Summary for " + currentDate + "\n");
        
        // Print purchases
        System.out.println("Purchases:\n");
        System.out.println(String.format("%-18s", "Flight Number") + String.format("%-18s", "Ticket Number") + String.format("%-18s", "Price"));
        for (int i = 0; i < boughtTickets.size(); i++) {
            System.out.println(String.format("%-18s", boughtTickets.get(i).getTicketNumber().substring(0, 6)) + String.format("%-18s", boughtTickets.get(i).getTicketNumber()) + String.format("%-18s", "$" + String.format("%.2f", boughtTickets.get(i).getPrice())));
            totalSales += boughtTickets.get(i).getPrice();
        }
        System.out.println("================================");
        System.out.println(String.format("%-36s", "Total Sales:") + "$" + String.format("%.2f", totalSales) + "\n");

        // Print refunds
        System.out.println("Refunds:\n");
        System.out.println(String.format("%-18s", "Flight Number") + String.format("%-18s", "Ticket Number") + String.format("%-18s", "Price"));
        for (int i = 0; i < refundedTickets.size(); i++) {
            System.out.println(String.format("%-18s", refundedTickets.get(i).getTicketNumber().substring(0, 6)) + String.format("%-18s", refundedTickets.get(i).getTicketNumber()) + String.format("%-18s", "$" + String.format("%.2f", refundedTickets.get(i).getPrice())));
            totalRefunds += refundedTickets.get(i).getPrice();
        }
        System.out.println("================================");
        System.out.println(String.format("%-36s", "Total Refunds:") + "$" + String.format("%.2f", totalRefunds) + "\n");

        // Print profit
        System.out.println("*****************************************");
        System.out.println(String.format("%-36s", "Profit:") + "$" + String.format("%.2f", totalSales - totalRefunds) + "\n");
        System.exit(0);
    }
}
