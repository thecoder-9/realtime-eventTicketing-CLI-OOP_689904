package test;

import main.TicketPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketPoolTest {

    private TicketPool ticketPool;

    @BeforeEach
    void setUp() {
        ticketPool = new TicketPool(
                "Vendor1", // vendor
                10, // maxTicketCapacity
                20, // totalTickets
                5, // ticketReleaseRate
                2, // customerRetrievalRate
                "Event" // title
        );
    }

    @Test
    void testAddTicketsSuccess() {
        ticketPool.addTickets(5);
        assertEquals(5, ticketPool.getTicketPoolSize(), "Pool size should be 5 after adding tickets.");
    }

    @Test
    void testAddTicketsExceedCapacity() {
        ticketPool.addTickets(10);
        ticketPool.addTickets(5); // Exceeds max capacity
        assertEquals(10, ticketPool.getTicketPoolSize(), "Pool size should not exceed max capacity.");
    }

    @Test
    void testAddTicketsNotEnoughRemaining() {
        ticketPool.addTickets(10); // All tickets released
        ticketPool.addTickets(5); // Attempt to add more
        assertEquals(10, ticketPool.getTicketPoolSize(), "Pool size should not exceed total tickets.");
    }

    @Test
    void testPurchaseTicketSuccess() {
        ticketPool.addTickets(5);
        ticketPool.purchaseTicket();
        assertEquals(3, ticketPool.getTicketPoolSize(), "Pool size should decrease by the customer retrieval rate.");
    }

    @Test
    void testPurchaseTicketEmptyPool() {
        ticketPool.purchaseTicket(); // Pool is initially empty
        assertEquals(0, ticketPool.getTicketPoolSize(), "Pool size should remain 0 if no tickets are available.");
    }

}