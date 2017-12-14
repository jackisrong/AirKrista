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

    public static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    public static ArrayList<Flight> flights = new ArrayList<Flight>();
    public static GregorianCalendar calendar = new GregorianCalendar();
    public static String todayDate = String.format("%2d", calendar.get(Calendar.DATE)) + "/" + String.format("%2d", calendar.get(Calendar.MONTH) + 1) + "/" + String.format("%4d", calendar.get(Calendar.YEAR));

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
        System.out.println("Choose the file that contains your database");

        // GET FILE
        BufferedReader inputStream = null;
        String line = null;

        try {
            inputStream = new BufferedReader(new FileReader("test.txt"));
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

        System.out.println(flights);
    }

    public static void displayArrivals() {
        System.out.println("Arrivals for today are:");
        System.out.println(String.format("%-15s", "Airline") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getStatus().equals("ARR") & flights.get(i).getDate().equals(todayDate)) {
                System.out.println(String.format("%-15s", flights.get(i).getAirline()) + String.format("%-18s", flights.get(i).getFlightNumber()) + String.format("%-18s", flights.get(i).getDestination()) + String.format("%-15s", flights.get(i).getDate()) + String.format("%-10s", flights.get(i).getTime()) + String.format("%-8s", flights.get(i).getTerminal()));
            }
        }
    }

    public static void displayDepartures() {
        System.out.println("Departures for today are:");
        System.out.println(String.format("%-15s", "Airline") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getStatus().equals("DEP") & flights.get(i).getDate().equals(todayDate)) {
                // REMOVE FLIGHTS THAT HAVE ALREADY DEPARTED
                System.out.println(String.format("%-15s", flights.get(i).getAirline()) + String.format("%-18s", flights.get(i).getFlightNumber()) + String.format("%-18s", flights.get(i).getDestination()) + String.format("%-15s", flights.get(i).getDate()) + String.format("%-10s", flights.get(i).getTime()) + String.format("%-8s", flights.get(i).getTerminal()));
            }
        }
    }

    public static void displayAirCanada() {
        System.out.println("All Air Canada flights for today are:");
        System.out.println(String.format("%-10s", "Status") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getAirline().equals("Air Canada") & flights.get(i).getDate().equals(todayDate)) {
                // REMOVE FLIGHTS THAT HAVE ALREADY ARRIVE OR DEPARTED
                System.out.println(String.format("%-10s", "DEP") + String.format("%-18s", "AC1234") + String.format("%-18s", "Vancouver") + String.format("%-15s", "20/12/2017") + String.format("%-10s", "15:03") + String.format("%-8s", "1"));
            }
        }
    }

    public static void purchaseTickets() {
        Scanner keyboard = new Scanner(System.in);
        ArrayList<Flight> availableFlights = new ArrayList<Flight>();
        Flight chosenFlight = null;
        String numberOfTickets = null;

        System.out.println("All departing Air Canada flights for today are:");
        System.out.println(String.format("%-10s", "Choice") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-12s", "Terminal") + String.format("%-15s", "Seats Left") + String.format("%-10s", "Price"));
        int choiceCounter = 0;
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getStatus().equals("DEP") & flights.get(i).getAirline().equals("Air Canada") & flights.get(i).getDate().equals(todayDate)) {
                // REMOVE FLIGHTS THAT ARE WITHIN 1 HOUR FROM NOW
                choiceCounter++;
                availableFlights.add(flights.get(i));
                System.out.println(String.format("%-10s", choiceCounter + ".") + String.format("%-18s", flights.get(i).getFlightNumber()) + String.format("%-18s", flights.get(i).getDestination()) + String.format("%-15s", flights.get(i).getDate()) + String.format("%-10s", flights.get(i).getTime()) + String.format("%-12s", flights.get(i).getTerminal()) + String.format("%-15s", flights.get(i).getSeats()) + String.format("%-10s", "$" + flights.get(i).getPrice()));
            }
        }

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

        System.out.println("\nWhat is your name?");
        String ticketName = keyboard.nextLine();

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
            Ticket ticket = new Ticket("AC1213:000", ticketName, 5.99);
            tickets.add(ticket);
        }

        System.out.println("\n=========================================================");
        System.out.println("Invoice:\n");

        System.out.println(String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-12s", "Terminal") + String.format("%-15s", "Quantity") + String.format("%-15s", "Price"));
        System.out.println(String.format("%-18s", chosenFlight.getFlightNumber()) + String.format("%-18s", chosenFlight.getDestination()) + String.format("%-15s", chosenFlight.getDate()) + String.format("%-10s", chosenFlight.getTime()) + String.format("%-12s", chosenFlight.getTerminal()) + String.format("%-15s", numberOfTickets) + String.format("%-15s", "$" + Integer.parseInt(numberOfTickets) * chosenFlight.getPrice()));

        System.out.println("\nYour ticket numbers are:");
        for (int i = 0; i < tickets.size(); i++) {
            // GET EXISTING TICKET NUMBERS, CREATE NEW UNTAKEN TICKET NUMBERS
        }

        System.out.println("\nThank you for your business, " + ticketName + "!");
        System.out.println("=======================================================");
    }

    public static void refundTickets() {
        Scanner keyboard = new Scanner(System.in);
        Ticket refundedTicket = null;
        boolean foundTicket = false;

        while (!foundTicket) {
            System.out.println("Please enter a valid ticket number:");
            String ticketNumber = keyboard.nextLine();
            for (int i = 0; i < tickets.size(); i++) {
                if (ticketNumber.equals(tickets.get(i).getTicketNumber())) {
                    refundedTicket = tickets.get(i);
                    foundTicket = true;
                    break;
                }
            }
            if (!foundTicket) {
                System.out.println("Sorry, invalid ticket number.");
            }
        }

        System.out.println("Your refund has been approved in the amount of $" + refundedTicket.getPrice() + ". Have a nice day " + refundedTicket.getName() + "!");
    }

    public static void logoff() {
        System.out.println("Summary for [DATE]\n");
        System.out.println("Purchases:\n");
        System.out.println(String.format("%-18s", "Flight Number") + String.format("%-18s", "Ticket Number") + String.format("%-18s", "Price"));
        System.exit(0);
    }
}
