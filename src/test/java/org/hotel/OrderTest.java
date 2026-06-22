package org.hotel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void testOrderStatusSetters() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Room room = new StandardRoom();
        Guest guest = new Guest("g4","Lee");
        Order order = new Order("o1", guest, room, java.util.List.of("x"));
        assertEquals(OrderStatus.PLACED, order.getStatus());
        order.setStatus(OrderStatus.PREPARING);
        assertEquals(OrderStatus.PREPARING, order.getStatus());
        order.setStatus(OrderStatus.READY);
        assertEquals(OrderStatus.READY, order.getStatus());
        order.setStatus(OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        assertNotNull(order.getDeliveredAt());
    }

    @Test
    public void testOrderConstructorStoresInitialData() {
        Guest guest = new Guest("g5","Lee");
        Room room = new StandardRoom();
        java.util.List<String> items = java.util.List.of("x", "y");

        Order order = new Order("o2", guest, room, items);

        assertEquals("o2", order.getId());
        assertEquals(guest, order.getGuest());
        assertEquals(room, order.getRoom());
        assertEquals(items, order.getItems());
        assertNotNull(order.getOrderedAt());
    }

    @Test
    public void testNonDeliveredStatusesDoNotSetDeliveredAt() {
        Order order = new Order("o3", new Guest("g6","Lee"), new StandardRoom(), java.util.List.of("x"));

        order.setStatus(OrderStatus.PREPARING);
        order.setStatus(OrderStatus.READY);

        assertNull(order.getDeliveredAt());
    }

    @Test
    public void testEmptyItemsOrderIsAccepted() {
        Order order = new Order("o4", new Guest("g7","Lee"), new StandardRoom(), java.util.List.of());

        assertTrue(order.getItems().isEmpty());
        assertEquals(OrderStatus.PLACED, order.getStatus());
    }

    @Test
    public void testSetPreparedAndDeliveredByStoreEmployees() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Order order = new Order("o5", new Guest("g8","Lee"), new StandardRoom(), java.util.List.of("x"));
        Cook cook = new Cook("c5","Chef","A", java.time.LocalDateTime.now(), "PB", "000", hotel);
        Server server = new Server("s5","Server","A", java.time.LocalDateTime.now(), "PB", "111", hotel);

        order.setPreparedBy(cook);
        order.setDeliveredBy(server);

        assertEquals(cook, order.getPreparedBy());
        assertEquals(server, order.getDeliveredBy());
    }

    @Test
    public void testIsActiveForNonFinalStatuses() {
        Order order = new Order("o8", new Guest("g11", "Lee"), new StandardRoom(), java.util.List.of("x"));
        assertTrue(order.isActive());
        order.setStatus(OrderStatus.PREPARING);
        assertTrue(order.isActive());
        order.setStatus(OrderStatus.READY);
        assertTrue(order.isActive());
    }

    @Test
    public void testIsActiveFalseAfterDelivered() {
        Order order = new Order("o9", new Guest("g12", "Lee"), new StandardRoom(), java.util.List.of("x"));
        order.setStatus(OrderStatus.DELIVERED);
        assertFalse(order.isActive());
    }

    @Test
    public void testIsActiveFalseAfterCancelled() {
        Order order = new Order("o10", new Guest("g13", "Lee"), new StandardRoom(), java.util.List.of("x"));
        order.cancel();
        assertFalse(order.isActive());
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    public void testCancelThrowsWhenAlreadyDelivered() {
        Order order = new Order("o11", new Guest("g14", "Lee"), new StandardRoom(), java.util.List.of("x"));
        order.setStatus(OrderStatus.DELIVERED);
        assertThrows(IllegalStateException.class, order::cancel);
    }

    @Test
    public void testGetElapsedTimeBeforeDeliveryIsPositive() {
        Order order = new Order("o12", new Guest("g15", "Lee"), new StandardRoom(), java.util.List.of("x"));
        java.time.Duration elapsed = order.getElapsedTime();
        assertFalse(elapsed.isNegative());
    }

    @Test
    public void testGetElapsedTimeAfterDeliveryIsFixed() throws InterruptedException {
        Order order = new Order("o13", new Guest("g16", "Lee"), new StandardRoom(), java.util.List.of("x"));
        order.setStatus(OrderStatus.DELIVERED);
        java.time.Duration elapsedFirst = order.getElapsedTime();
        Thread.sleep(50);
        java.time.Duration elapsedSecond = order.getElapsedTime();
        assertEquals(elapsedFirst, elapsedSecond);
    }
}
