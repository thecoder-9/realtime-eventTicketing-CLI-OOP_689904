package main;

import java.io.Serializable;
import java.util.Scanner;

public class Configuration implements Serializable {
    private static int ticketIdCounter = 1;
    private final int eventTicketId;
    private String vendorName;
    private String title;
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int ticketReleaseInterval;
    private int customerRetrievalRate;
    private int customerRetrievalInterval;
    private int totalTickets;

    public Configuration() {
        this.eventTicketId = ticketIdCounter++;
    }

    // Helper method to get valid integer input
    private int getValidIntegerInput(String prompt, Scanner scanner) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return value;
    }

    // Loading the main prompt
    public void promptForInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Event Title: ");
        this.title = scanner.nextLine();

        System.out.print("Enter main.Vendor Name: ");
        this.vendorName = scanner.nextLine();

        this.totalTickets = getValidIntegerInput("Enter Total Number of Tickets: ", scanner);
        this.maxTicketCapacity = getValidIntegerInput("Enter Max Ticket Capacity: ", scanner);
        this.ticketReleaseRate = getValidIntegerInput("Enter Ticket Release Rate: ", scanner);
        this.ticketReleaseInterval = getValidIntegerInput("Enter Ticket Release Interval (in ms): ", scanner);
        this.customerRetrievalRate = getValidIntegerInput("Enter main.Customer Retrieval Rate: ", scanner);
        this.customerRetrievalInterval = getValidIntegerInput("Enter main.Customer Retrieval Interval (in ms): ", scanner);
    }

    public int getEventTicketId() {
        return eventTicketId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getTicketReleaseInterval() {
        return ticketReleaseInterval;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getCustomerRetrievalInterval() {
        return customerRetrievalInterval;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    // Display the loaded configurations
    @Override
    public String toString() {
        return String.format(
                "\nEvent Title: %s, Event Ticket ID: %d, main.Vendor Name: %s, Max Capacity: %d, Release Rate: %d, Release Interval: %d ms, " +
                        "Retrieval Rate: %d, Retrieval Interval: %d ms, Total Tickets: %d",
                title, eventTicketId, vendorName, maxTicketCapacity, ticketReleaseRate, ticketReleaseInterval,
                customerRetrievalRate, customerRetrievalInterval, totalTickets
        );
    }
}
