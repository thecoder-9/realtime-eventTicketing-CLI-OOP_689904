package main;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate; 
    private final int retrievalInterval; 

    public Customer(TicketPool ticketPool, int retrievalRate, int retrievalInterval) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.retrievalInterval = retrievalInterval;
    }

    // Run purchase ticket method
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Use the purchaseTickets method from main.TicketPool
                ticketPool.purchaseTicket();
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            System.out.println("main.Customer thread interrupted.");
        }
    }
}
