package main;

import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private final String vendor;
    private final int maxTicketCapacity;
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final int customerRetrievalRate;
    private final String title;
    private final List<Integer> ticketPool;
    private boolean simulationComplete = false;
    private int ticketsSold = 0;
    private int customers = 0;

    // Constructor
    public TicketPool(String vendor, int maxTicketCapacity, int totalTickets, int ticketReleaseRate, int customerRetrievalRate, String title) {
        this.vendor = vendor;
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.title = title;
        this.ticketPool = new ArrayList<>();
    }

    // Method for adding the ticket
    public synchronized void addTickets(int ticketCount) {
        if (simulationComplete) return;

        // Calculate remaining tickets to be released
        int ticketsRemainingToBeReleased = totalTickets - ticketsSold - ticketPool.size();

        // Check available space in the pool
        int availableSpace = maxTicketCapacity - ticketPool.size();

        // Condition to check if the pool is full or there are no tickets left to release
        if (availableSpace < ticketCount  ) {
            System.out.println("The ticket pool is full. Cannot add more tickets to the ticket pool.");
            return; // Exit if the pool cannot accommodate 5 tickets
        }
        if(ticketsRemainingToBeReleased < ticketCount) {
            System.out.println("There are no tickets to release.");
            return; // Exit if the pool cannot accommodate 5 tickets
        }

        // Add tickets to the pool
        for (int i = 0; i < ticketCount; i++) {
            int ticketId = (int) (Math.random() * 1000); 
            ticketPool.add(ticketId);
        }

        System.out.println("Tickets remaining to be released: "+ ticketsRemainingToBeReleased);
        System.out.println("main.Vendor [" + vendor + "] released " + ticketCount + " " + title + " ticket(s).");
        System.out.println("Current pool size: " + ticketPool.size() + "/" + maxTicketCapacity + ".");
    }

    // Updated purchaseTicket method
    public synchronized void purchaseTicket() {
        if (simulationComplete) return;

        // Check if the pool is empty
        if (ticketPool.isEmpty()) {
            System.out.println("The ticket pool is empty. No more tickets available for purchase in the ticket pool.");
            return;
        }

        int ticketsRetrieved = 0;

        // Retrieve tickets based on the customer's retrieval rate
        while (!ticketPool.isEmpty() && ticketsRetrieved < customerRetrievalRate) {
            ticketPool.remove(0); // Retrieve one ticket from the pool
            ticketsRetrieved++;
            ticketsSold++;
        }

        // Log ticket retrieval
        if (ticketsRetrieved > 0) {
            customers++;
            System.out.println("main.Customer [" + customers + "] retrieved " + ticketsRetrieved + " " + title + " ticket(s).");
        }

        // Stop simulation if all tickets are sold
        if (ticketPool.isEmpty() && ticketsSold >= totalTickets) {
            System.out.println("Simulation Completed.");
            stopSimulation();
        }

        System.out.println("Current pool size: " + ticketPool.size() + "/" + maxTicketCapacity + ".");

    }

    // Stop the simulation
    public void stopSimulation() {
        simulationComplete = true;
        System.out.println("Simulation Stopped.");
    }

    // Get current size of the ticket pool
    public int getTicketPoolSize() {
        if (simulationComplete) {
            return 0; // Return 0 if simulation is complete
        }
        return ticketPool.size();
    }

    
    // Method to check whether the simulation is complete 
    public boolean isSimulationComplete() {
        return simulationComplete;
    }

}
