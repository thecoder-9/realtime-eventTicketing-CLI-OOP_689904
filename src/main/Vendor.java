package main;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseRate;
    private final int releaseInterval; 

    // Constructor to initialize the main.Vendor
    public Vendor(TicketPool ticketPool, int releaseRate, int releaseInterval ) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.releaseInterval = releaseInterval; 
    }

    @Override
    public void run() {
        try {
            // Loop that continues releasing tickets until the thread is interrupted
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.addTickets(releaseRate); 
                Thread.sleep(releaseInterval); 
            }
        } catch (InterruptedException e) {
            // Properly handle interruption
            System.out.println("main.Vendor thread interrupted. Exiting...");
            Thread.currentThread().interrupt(); 
        }
    }
}
