package org.hotel;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuestTest {

    @Test
    public void testBookAddsRoom() {
        var guest = new Guest("g1", "Alice");
        var room = new StandardRoom();
        guest.book(room);
        assertTrue(guest.getBookedRooms().contains(room));
    }

    @Test
    public void testGuestStartsWithNoRoomsAndNoOrders() {
        var guest = new Guest("g0", "Alice");

        assertTrue(guest.getBookedRooms().isEmpty());
        assertTrue(guest.getOrders().isEmpty());
    }

    @Test
    public void testPlaceOrderWithReservation() {
        var hotel = new Hotel("H","L","A","P",4,5,null);
        var r = new StandardRoom();
        hotel.addRoom(r);
        var guest = new Guest("g2","Bob");
        hotel.register(guest);
        hotel.book(guest, r);
        Order o = guest.placeOrder("o2", List.of("tea"), hotel);
        assertNotNull(o);
        assertEquals(guest, o.getGuest());
        assertEquals(r, o.getRoom());
    }

    @Test
    public void testPlaceOrderWithoutReservationThrowsAndDoesNotStoreOrder() {
        var hotel = new Hotel("H","L","A","P",4,5,null);
        var guest = new Guest("g3","Bob");

        assertThrows(IllegalStateException.class, () -> guest.placeOrder("o3", List.of("tea"), hotel));
        assertTrue(guest.getOrders().isEmpty());
    }

    @Test
    public void testPlaceOrderStoresOrderAndPreservesItems() {
        var hotel = new Hotel("H","L","A","P",4,5,null);
        var room = new StandardRoom();
        var guest = new Guest("g4","Bob");
        hotel.book(guest, room);

        Order order = guest.placeOrder("client-id", List.of("tea", "cake"), hotel);

        assertTrue(guest.getOrders().contains(order));
        assertEquals(List.of("tea", "cake"), order.getItems());
        assertNotEquals("client-id", order.getId());
    }
}
