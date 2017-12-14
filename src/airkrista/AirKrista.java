package airkrista;
//Krista Does Not Poop

import java.util.*;

public class AirKrista {

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

            input = keyboard.nextInt();

            switch (input) {
                case 1:
                    updateDatabase();
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
        }
    }

    // Complete the folllowing methods
    public static void updateDatabase() {
        System.out.println("Choose the file that contains your database");
        
        // GET FILE
        
        String exampleFileLine = "DEP,Lufthansa,LU9999,Miami,21/11/2015,22:00,2,B747,$989.73";
        String[] splitFile = new String[9];
        StringTokenizer split = new StringTokenizer(exampleFileLine, ",");
        int numberOfTokens = split.countTokens();
        for (int i = 0; i < numberOfTokens; i++) {
            splitFile[i] = split.nextToken();
            System.out.println(splitFile[i]);
        }
    }

    public static void displayArrivals() {
        System.out.println("Arrivals for today are:");
        
        // AIRLINE, FLIGHT NUMBER, DESTINATION, DATE, TIME, TERMINAL
    }

    public static void displayDepartures() {
        System.out.println("Departures for today are:");
        
        // AIRLINE, FLIGHT NUMBER, DESTINATION, DATE, TIME, TERMINAL
    }

    public static void displayAirCanada() {
        System.out.println("All Air Canada flights for today are:");
        
        // STATUS, FLIGHT NUMBER, DESTINATION, DATE, TIME, TERMINAL
    }

    public static void purchaseTickets() {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("All departing Air Canada flights for today are:");

        // PRINT FLIGHTS AT LEAST 1 HOUR FROM NOW HERE
        
        System.out.println("Which flight would you like?");
        int flightChoice = keyboard.nextInt();
        System.out.println("What is your name?");
        String ticketName = keyboard.nextLine();
        System.out.println("How many tickets would you like to purchase?");
        int numberOfTickets = keyboard.nextInt();

        //Ticket ticket = new Ticket(number, name, price);
        // DISPLAY INVOICE HERE
    }

    public static void refundTickets() {
        System.out.println("Please enter a valid ticket number:");
    }

    public static void logoff() {
        System.out.println("Summary for [DATE]\n");
        System.out.println("Purchases:\n");
        System.exit(0);
    }
}
