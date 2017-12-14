package airkrista;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AirKrista {

    public static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    public static ArrayList<Flight> flights = new ArrayList<Flight>();

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

        System.out.println(String.format("%-15s", "Air Canada") + String.format("%-18s", "AC1234") + String.format("%-18s", "Vancouver") + String.format("%-15s", "20/12/2017") + String.format("%-10s", "15:03") + String.format("%-8s", "1"));
    }

    public static void displayDepartures() {
        System.out.println("Departures for today are:");

        System.out.println(String.format("%-15s", "Airline") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
    }

    public static void displayAirCanada() {
        GregorianCalendar calendar = new GregorianCalendar();

        System.out.println("All Air Canada flights for today are:");

        System.out.println(String.format("%-10s", "Status") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-8s", "Terminal"));
        System.out.println(String.format("%-10s", "DEP") + String.format("%-18s", "AC1234") + String.format("%-18s", "Vancouver") + String.format("%-15s", "20/12/2017") + String.format("%-10s", "15:03") + String.format("%-8s", "1"));
    }

    public static void purchaseTickets() {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("All departing Air Canada flights for today are:");

        System.out.println(String.format("%-10s", "Choice") + String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-12s", "Terminal") + String.format("%-15s", "Seats Left") + String.format("%-10s", "Price"));
        System.out.println(String.format("%-10s", "1.") + String.format("%-18s", "AC1234") + String.format("%-18s", "Vancouver") + String.format("%-15s", "20/12/2017") + String.format("%-10s", "12:00") + String.format("%-12s", "1") + String.format("%-15s", "300") + String.format("%-10s", "$415.99"));
        // PRINT FLIGHTS AT LEAST 1 HOUR FROM NOW HERE

        System.out.println("Which flight would you like?");
        String flightChoice = keyboard.nextLine();
        // CHECK IF FLIGHT IS VALID

        System.out.println("What is your name?");
        String ticketName = keyboard.nextLine();
        // CHECK IF NAME IS VALID

        System.out.println("How many tickets would you like to purchase?");
        String numberOfTickets = keyboard.nextLine();
        // CHECK IF # OF TICKETS IS <= # OF SEATS

        for (int i = 0; i < Integer.parseInt(numberOfTickets); i++) {
            Ticket ticket = new Ticket("AC1213:000", ticketName, 5.99);
            tickets.add(ticket);
        }

        System.out.println("\n=========================================================");
        System.out.println("Invoice:\n");

        System.out.println(String.format("%-18s", "Flight Number") + String.format("%-18s", "Destination") + String.format("%-15s", "Date") + String.format("%-10s", "Time") + String.format("%-12s", "Terminal") + String.format("%-15s", "Quantity") + String.format("%-15s", "Price"));
        System.out.println(String.format("%-18s", "AC1234") + String.format("%-18s", "Vancouver") + String.format("%-15s", "20/12/2017") + String.format("%-10s", "45:22") + String.format("%-12s", "1") + String.format("%-15s", "2") + String.format("%-15s", "$1159.36"));
        // DISPLAY BOUGHT TICKETS

        System.out.println("Your ticket numbers are:");
        // PRINT TICKET NUMBERS

        System.out.println("Thank you for your business, " + ticketName + "!");
        System.out.println("=======================================================");
    }

    public static void refundTickets() {
        System.out.println("Please enter a valid ticket number:");
    }

    public static void logoff() {
        System.out.println("Summary for [DATE]\n");
        System.out.println("Purchases:\n");
        System.out.println(String.format("%-18s", "Flight Number") + String.format("%-18s", "Ticket Number") + String.format("%-18s", "Price"));
        System.exit(0);
    }
}
