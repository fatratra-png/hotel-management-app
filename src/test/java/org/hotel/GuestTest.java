package org.hotel;

import org.junit.jupiter.api.Test;
import org.hotel.order.OrderItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuestTest {

    private static final OrderItem TEA = new OrderItem("tea", 3.0);
    private static final OrderItem CAKE = new OrderItem("cake", 5.0);

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
        var hotel = new Hotel("H","L","A","P",4,5);
        var r = new StandardRoom();
        hotel.addRoom(r);
        var guest = new Guest("g2","Bob");
        hotel.register(guest);
        hotel.book(guest, r);
        Order o = guest.placeOrder(List.of(TEA), hotel);
        assertNotNull(o);
        assertEquals(guest, o.getGuest());
        assertEquals(r, o.getRoom());
    }

    @Test
    public void testPlaceOrderWithoutReservationThrowsAndDoesNotStoreOrder() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var guest = new Guest("g3","Bob");

        assertThrows(IllegalStateException.class, () -> guest.placeOrder(List.of(TEA), hotel));
        assertTrue(guest.getOrders().isEmpty());
    }

    @Test
    public void testPlaceOrderStoresOrderAndPreservesItems() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var room = new StandardRoom();
        var guest = new Guest("g4","Bob");
        hotel.book(guest, room);

        Order order = guest.placeOrder(List.of(TEA, CAKE), hotel);

        assertTrue(guest.getOrders().contains(order));
        assertEquals(List.of(TEA, CAKE), order.getItems());
    }
}
