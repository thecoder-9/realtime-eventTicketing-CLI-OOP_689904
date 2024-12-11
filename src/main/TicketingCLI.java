package main;

import java.util.Scanner;

public class TicketingCLI {

    public static void main(String[] args) {
        TicketingManager ticketingManager = new TicketingManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("|||Welcome to the Real-time Event ticketing System|||");

        while (true) {
            // Display menu options
            System.out.println("\n\n===== Ticket Management System =====");
            System.out.println("    1. Add main.Configuration");
            System.out.println("    2. Load Configurations");
            System.out.println("    3. Display Configurations");
            System.out.println("    4. Start Simulation");
            System.out.println("    5. Stop Simulation");
            System.out.println("    6. Exit");
            System.out.println("====================================");
            System.out.print("\nEnter option: ");

            // Get user input
            int option = scanner.nextInt();
            System.out.println();

            // Execute the corresponding action based on the user's choice using switch case
            switch (option) {
                case 1 -> ticketingManager.addConfiguration();
                case 2 -> ticketingManager.loadConfigurations();
                case 3 -> ticketingManager.displayConfigurations();
                case 4 -> ticketingManager.startSimulation();
                case 5 -> ticketingManager.stopSimulation();
                case 6 -> {
                    System.out.println("Thank you for using our service! Quitting the system.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please select a valid option from the menu.\n");
            }
        }
    }
}
