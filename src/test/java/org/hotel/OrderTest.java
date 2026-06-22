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
}
