package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketingManager {

    private List<Configuration> configurations = new ArrayList<>();
    private static volatile boolean isRunning = false;
    private static TicketPool ticketPool = null;
    private Thread vendorThread = null;
    private Thread customerThread = null;
    private final Scanner scanner = new Scanner(System.in);

    // Add configurations to a file
    public void addConfiguration() {
        Configuration config = new Configuration();
        config.promptForInput();
        configurations.add(config);

        try {
            ConfigurationManager.saveToJsonFile("configurations.json", configurations);
            System.out.println("main.Configuration added and saved.\n");
        } catch (IOException e) {
            System.out.println("Failed to save configurations: " + e.getMessage() + "\n");
        }
    }

    // Load Configurations from a file
    public void loadConfigurations() {
        try {
            configurations = ConfigurationManager.loadFromJsonFile("configurations.json");
            System.out.println("Configurations loaded successfully.\n");
        } catch (IOException e) {
            System.out.println("Failed to load configurations: " + e.getMessage() + "\n");
        }
    }

    // Display Configurations
    public void displayConfigurations() {
        if (configurations.isEmpty()) {
            System.out.println("There are no available configurations.\n");
        } else {
            System.out.println("Saved Configurations:");
            configurations.forEach(System.out::println);
            System.out.println();
        }
    }

    // Start the simulation
    public void startSimulation() {
        if (configurations.isEmpty()) {
            System.out.println("Please add or load configurations before starting a simulation.\n");
            return;
        }

        System.out.print("Enter Event Ticket ID to start simulation: ");
        int ticketId = scanner.nextInt();

        Configuration selectedConfig = configurations.stream()
                .filter(c -> c.getEventTicketId() == ticketId)
                .findFirst()
                .orElse(null);

        if (selectedConfig == null) {
            System.out.println("Invalid Event Ticket ID.\n");
            return;
        }

        if (isRunning) {
            System.out.println("Simulation is already running.\n");
            return;
        }

        ticketPool = new TicketPool(
                selectedConfig.getVendorName(),
                selectedConfig.getMaxTicketCapacity(),
                selectedConfig.getTotalTickets(),
                selectedConfig.getTicketReleaseRate(),
                selectedConfig.getCustomerRetrievalRate(),
                selectedConfig.getTitle()
        );
        System.out.println("Ticket pool initialized with a capacity of " + selectedConfig.getMaxTicketCapacity() + ".");

        isRunning = true;

        vendorThread = new Thread(new Vendor(ticketPool, selectedConfig.getTicketReleaseRate(), selectedConfig.getTicketReleaseInterval()));
        customerThread = new Thread(new Customer(ticketPool, selectedConfig.getCustomerRetrievalRate(), selectedConfig.getCustomerRetrievalInterval()));

        // Starting the vendor thread
        vendorThread.start();
        
        // starting the cudtomer thread
        customerThread.start();

        System.out.println("main.Vendor and main.Customer threads started.");

        // Monitor Thread
        new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(2000);// Monitor thread in each 2 second
                    if (ticketPool != null) {
                        int currentTicketCount = ticketPool.getTicketPoolSize();
                        if (currentTicketCount == 0 && !ticketPool.isSimulationComplete()) {
                            ticketPool.stopSimulation();
                            isRunning = false;
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Monitoring interrupted.");
                }
            }
        }).start();
    }

    public void stopSimulation() {
        if (!isRunning) {
            System.out.println("Simulation is not running.\n");
            return;
        }

        isRunning = false;

        if (vendorThread != null) {
            vendorThread.interrupt();
        }
        if (customerThread != null) {
            customerThread.interrupt();
        }

        if (ticketPool != null) {
            ticketPool.stopSimulation();
        }

        System.out.println("Simulation stopped.\n");
    }
}
