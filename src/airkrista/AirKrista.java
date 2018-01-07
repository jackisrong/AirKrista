package airkrista;

// FOR TIME DIFFERENCES (1 HOUR/12 HOUR), CAN POSSIBLY USE A DECIMAL SYSTEM INSTEAD, THEN COMPARE USING >= OR <=
// EXAMPLE: 06:56 would be 6.56, 21:22 would be 21.22, then it can be compared as an int
// instead of using substring then doing 3 different if cases (hour <, hour = minutes <, and hour = minutes >)
// THIS WOULD POSSIBLY HELP MINIMIZE LINES AND MAKE IT LESS COMPLEX
// BUT THIS ISN'T NECESSARY IF EVERYTHING WORKS 100% OF THE TIME

// ALSO SEE GLOBAL VARIABLES FOR AN ISSUE WE MAY HAVE TO CHANGE

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AirKrista {

    public static ArrayList<Flight> flights = new ArrayList<Flight>();
    public static ArrayList<Ticket> boughtTickets = new ArrayList<Ticket>();
    public static ArrayList<Ticket> validTickets = new ArrayList<Ticket>();
    public static ArrayList<Ticket> refundedTickets = new ArrayList<Ticket>();
    public static GregorianCalendar calendar = new GregorianCalendar();
    public static Flight chosenFlight = null;
    public static int numberOfTickets = 0;
    public static String currentTime = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE));
    // ^^^ DOING IT LIKE THIS SETS THE CURRENTTIME TO WHENEVER THE PROGRAM GETS RUN ONLY
    // AND NOT WHEN A CERTAIN MENU OPTION GETS CHOSEN, SCREWING UP THE ACCURACY OF THE PROGRAM
    // EXAMPLE: the program gets ran at 10:22 but you choose to buy a ticket at 10:24
    // the code compares currentTime as 10:22 but really it should be 10:24 because that's when the ticket is being bought
    // POSSIBLE FIX IS TO GIVE CURRENTTIME ITS VALUE INSIDE EACH METHOD WHERE IT'S USED INSTEAD OF GLOBALLY
    
    public static void main(String args[]) {
        Scanner keyboard = new Scanner(System.in);
        int input = 0;

        while (true) {
            System.out.println("************ MAIN MENU ************");
            System.out.println("1. Update Database");
            System.out.println("2. Display Arrivals");
            System.out.println("3. Display Departures");
            System.out.println("4. Display Air Canada Flights");
            System.out.println("5. Purchase Tickets");
            System.out.println("6. Refund Tickets");
            System.out.println("7. Logoff");

            try {
                input = keyboard.nextInt();
                switch (input) {
                    case 1:
                        try {
                            updateDatabase();
                        } catch (IOException e) {
                            System.out.println("ERROR: IOException in main switch.");
                        }
                        break;
                    case 2:
                        displayArrivals();
                        break;
                    case 3:
                        displayDepartures();
                        break;
                    case 4:
                        displayAirCanada();
                        break;
                    case 5:
                        purchaseTickets();
                        break;
                    case 6:
                        refundTickets();
                        break;
                    case 7:
                        logoff();
                        break;
                    default:
                        System.out.println("Invalid menu entry!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid menu entry!");
                keyboard.next();
            }
        }
    }

    public static void updateDatabase() throws IOException {
        Scanner keyboard = new Scanner(System.in);
        BufferedReader inputStream = null;
        String line;

        // Clear flights and ask for database file name
        flights.clear();
        System.out.println("Enter file name of your database: (must be located in project folder)");
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
            System.out.println("ERROR: Cannot open file.");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static void displayArrivals() {
        // Print all of todays arrivals
        System.out.println("Arrivals for today are:");
        System.out.println(String.format("%-15s", "Airline") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
        for (int i = 0; i < flights.size(); i++) {
            if (!flights.get(i).isDeparture() & flights.get(i).isToday()) {
                System.out.println(String.format("%-15s", flights.get(i).getAirline()) + String.format("%-18s", flights.get(i).getFlightNumber()) + String.format("%-18s", flights.get(i).getDestination()) + String.format("%-15s", flights.get(i).getDate()) + String.format("%-10s", flights.get(i).getTime()) + String.format("%-8s", flights.get(i).getTerminal()));
            }
        }
    }

    public static void displayDepartures() {
        // Print all of today's departures except for ones already departed
        System.out.println("Departures for today are:");
        System.out.println(String.format("%-15s", "Airline") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).isDeparture() & flights.get(i).isToday()) {
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
        // Print all of today's Air Canada flights except already arrived or departed
        System.out.println("All Air Canada flights for today are:");
        System.out.println(String.format("%-10s", "Status") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getAirline().equals("Air Canada") & flights.get(i).isToday()) {
                if (Integer.parseInt(flights.get(i).getTime().substring(0, 2)) < Integer.parseInt(currentTime.substring(0, 2))) {
                    continue;
                } else if (Integer.parseInt(flights.get(i).getTime().substring(0, 2)) == Integer.parseInt(currentTime.substring(0, 2))) {
                    if (Integer.parseInt(flights.get(i).getTime().substring(3, 5)) < Integer.parseInt(currentTime.substring(3, 5))) {
                        continue;
                    }
                }
                System.out.println(String.format("%-10s", flights.get(i).getStatus()) + String.format("%-18s", flights.get(i).getFlightNumber()) + String.format("%-18s", flights.get(i).getDestination()) + String.format("%-15s", flights.get(i).getDate()) + String.format("%-10s", flights.get(i).getTime()) + String.format("%-8s", flights.get(i).getTerminal()));
            }
        }
    }

    public static void purchaseTickets() {
        Scanner keyboard = new Scanner(System.in);
        ArrayList<Flight> availableFlights = new ArrayList<Flight>();
        ArrayList<String> ticketNumbersThisSession = new ArrayList<String>();

        // Print all of today's departing Air Canada flights 1 hour or more before flight time
        System.out.println("All departing Air Canada flights for today are:");
        System.out.println(String.format("%-10s", "Choice") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-12s", "Terminal") + String.format("%-15s", "Seats Left") + String.format("%-10s", "Price"));
        int choiceCounter = 0;
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).isDeparture() & flights.get(i).getAirline().equals("Air Canada") & flights.get(i).isToday()) {
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
            try {
                int flightChoice = keyboard.nextInt();
                if (flightChoice <= choiceCounter & flightChoice > 0 & flights.get(flightChoice).getSeats() > 0) {
                    chosenFlight = availableFlights.get(flightChoice - 1);
                    break;
                } else if (flightChoice <= choiceCounter & flightChoice > 0 & flights.get(flightChoice).getSeats() == 0) {
                    System.out.println("Sorry, this flight is sold out!\n");
                } else {
                    System.out.println("Sorry, that's an invalid choice.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Sorry, that's an invalid choice.\n");
                keyboard.next();
            }
        }

        // Ask for name
        keyboard.nextLine();
        System.out.println("\nWhat is your name?");
        String ticketName = keyboard.nextLine();

        // Ask for number of tickets
        while (true) {
            System.out.println("\nHow many tickets would you like to purchase?");
            try {
                numberOfTickets = keyboard.nextInt();
                if (numberOfTickets <= chosenFlight.getSeats() & numberOfTickets > 0) {
                    break;
                } else {
                    System.out.println("Sorry, that's an invalid choice.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Sorry, that's an invalid choice.\n");
                keyboard.next();
            }
        }

        // Create ticket numbers
        for (int i = 0; i < numberOfTickets; i++) {
            Ticket ticket = new Ticket(chosenFlight.getFlightNumber() + ":" + String.format("%03d", chosenFlight.getBoughtTickets()), ticketName, chosenFlight.getPrice());
            ticketNumbersThisSession.add(chosenFlight.getFlightNumber() + ":" + String.format("%03d", chosenFlight.getBoughtTickets()));
            boughtTickets.add(ticket);
            validTickets.add(ticket);
            chosenFlight.addBoughtTickets(1);
        }

        // Update number of seats in flight
        chosenFlight.setSeats(chosenFlight.getSeats() - numberOfTickets);

        // Print invoice
        System.out.println("\n=========================================================");
        System.out.println("Invoice:\n");
        System.out.println(String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-12s", "Terminal") + String.format("%-15s", "Quantity") + String.format("%-15s", "Price"));
        System.out.println(String.format("%-18s", chosenFlight.getFlightNumber()) + String.format("%-18s", chosenFlight.getDestination()) + String.format("%-15s", chosenFlight.getDate()) + String.format("%-10s", chosenFlight.getTime()) + String.format("%-12s", chosenFlight.getTerminal()) + String.format("%-15s", numberOfTickets) + String.format("%-15s", "$" + String.format("%.2f", Math.round(numberOfTickets * chosenFlight.getPrice() * 100) / 100.0)));
        System.out.println("\nYour ticket numbers are:");
        for (int i = 0; i < ticketNumbersThisSession.size(); i++) {
            System.out.println(ticketNumbersThisSession.get(i));
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
            for (int i = 0; i < validTickets.size(); i++) {
                if (ticketNumber.equals(validTickets.get(i).getTicketNumber())) {
                    if (Integer.parseInt(currentTime.substring(0, 2)) > Integer.parseInt(validTickets.get(i).getTime().substring(0, 2)) - 12) {
                        System.out.println("Sorry, it must be at least 12 hours before flight time to refund!");
                    } else if (Integer.parseInt(currentTime.substring(0, 2)) == Integer.parseInt(validTickets.get(i).getTime().substring(0, 2)) - 12) {
                        if (Integer.parseInt(currentTime.substring(3, 5)) > Integer.parseInt(flights.get(i).getTime().substring(3, 5))) {
                            System.out.println("Sorry, it must be at least 12 hours before flight time to refund!");
                        }
                    } else {
                        refundedTicket = validTickets.get(i);
                        validTickets.remove(i);
                        refundedTickets.add(refundedTicket);
                        chosenFlight.setSeats(chosenFlight.getSeats() + 1);
                        System.out.println("Your refund has been approved in the amount of $" + String.format("%.2f", refundedTicket.getPrice()) + ". Have a nice day, " + refundedTicket.getName() + "!");
                    }
                    foundTicket = true;
                    break;
                }
            }

            if (!foundTicket) {
                System.out.println("Sorry, invalid ticket number.\n");
            }
        }
    }

    public static void logoff() {
        String currentDate = String.format("%02d", calendar.get(Calendar.DATE)) + "/" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "/" + String.format("%4d", calendar.get(Calendar.YEAR));
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
