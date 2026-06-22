package org.hotel;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HotelTest {

    @Test
    public void testReserveAndOrderAndDeliverFlow() {
        Hotel hotel = new Hotel("H", "L", "A", "P", 4, 10, null);
        Room room = new StandardRoom();
        hotel.addRoom(room);

        Guest guest = new Guest("g1", "Alice");
        hotel.register(guest);

        hotel.book(guest, room);
        assertTrue(room.isOccupied());
        assertTrue(guest.getBookedRooms().contains(room));

        Order order = guest.placeOrder("o1", List.of("coffee", "sandwich"), hotel);
        assertNotNull(order);
        assertEquals(OrderStatus.PLACED, order.getStatus());

        Cook cook = new Cook("c1", "Cooky", "S", LocalDateTime.now(), "PB", "000", hotel);
        Server server = new Server("s1", "Serve", "T", LocalDateTime.now(), "PB", "111", hotel);
        hotel.addEmployee(cook);
        hotel.addEmployee(server);

        hotel.deliverToRoom(order);

        assertEquals(cook, order.getPreparedBy());
        assertEquals(server, order.getDeliveredBy());
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        assertTrue(order.getRoom().getDeliveries().contains(order));
        assertNotNull(order.getDeliveredAt());
    }

    @Test
    public void testConstructorValidations() {
        assertThrows(IllegalArgumentException.class, () -> new Hotel("H","L","A","P", -1, 5, null));
        assertThrows(IllegalArgumentException.class, () -> new Hotel("H","L","A","P", 6, 5, null));
    }

    @Test
    public void testConstructorAcceptsStarCountBoundaries() {
        Hotel zeroStar = new Hotel("H","L","A","P", 0, 5, null);
        Hotel fiveStar = new Hotel("H","L","A","P", 5, 5, null);

        assertEquals(0, zeroStar.getStarCount());
        assertEquals(5, fiveStar.getStarCount());
    }

    @Test
    public void testReserveOccupiedRoomThrowsAndDoesNotBookSecondGuest() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,null);
        Room room = new StandardRoom();
        Guest first = new Guest("g1", "Alice");
        Guest second = new Guest("g2", "Bob");
        hotel.reserveRoom(first, room);

        assertThrows(IllegalStateException.class, () -> hotel.reserveRoom(second, room));
        assertTrue(first.getBookedRooms().contains(room));
        assertFalse(second.getBookedRooms().contains(room));
    }

    @Test
    public void testCommandToRoomWithoutReservationThrows() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,null);
        Guest guest = new Guest("g1", "Alice");

        assertThrows(IllegalStateException.class, () -> hotel.commandToRoom(guest, List.of("coffee")));
        assertTrue(hotel.getOrders().isEmpty());
    }

    @Test
    public void testCommandToRoomStoresOrderWithGeneratedEightCharacterId() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,null);
        Room room = new StandardRoom();
        Guest guest = new Guest("g1", "Alice");
        hotel.reserveRoom(guest, room);

        Order order = hotel.commandToRoom(guest, List.of("coffee"));

        assertEquals(8, order.getId().length());
        assertTrue(hotel.getOrders().contains(order));
        assertEquals(List.of("coffee"), order.getItems());
    }

    @Test
    public void testDeliverToRoomWithoutStaffLeavesOrderPlaced() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,null);
        Room room = new StandardRoom();
        Guest guest = new Guest("g1", "Alice");
        Order order = new Order("o1", guest, room, List.of("coffee"));

        hotel.deliverToRoom(order);

        assertEquals(OrderStatus.PLACED, order.getStatus());
        assertNull(order.getPreparedBy());
        assertNull(order.getDeliveredBy());
        assertTrue(room.getDeliveries().isEmpty());
    }

    @Test
    public void testDeliverToRoomWithOnlyCookPreparesButDoesNotDeliver() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,null);
        Cook cook = new Cook("c1", "Cooky", "S", LocalDateTime.now(), "PB", "000", hotel);
        Order order = new Order("o1", new Guest("g1", "Alice"), new StandardRoom(), List.of("coffee"));
        hotel.addEmployee(cook);

        hotel.deliverToRoom(order);

        assertEquals(OrderStatus.READY, order.getStatus());
        assertEquals(cook, order.getPreparedBy());
        assertNull(order.getDeliveredBy());
    }

    @Test
    public void testDeliverToRoomWithOnlyServerDeliversWithoutPreparedBy() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,null);
        Server server = new Server("s1", "Serve", "T", LocalDateTime.now(), "PB", "111", hotel);
        Room room = new StandardRoom();
        Order order = new Order("o1", new Guest("g1", "Alice"), room, List.of("coffee"));
        hotel.addEmployee(server);

        hotel.deliverToRoom(order);

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        assertNull(order.getPreparedBy());
        assertEquals(server, order.getDeliveredBy());
        assertTrue(room.getDeliveries().contains(order));
    }

    @Test
    public void testRegisterAndAddRoomStoreUniqueInstances() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,null);
        Guest guest = new Guest("g1", "Alice");
        Room room = new StandardRoom();

        hotel.register(guest);
        hotel.register(guest);
        hotel.addRoom(room);
        hotel.addRoom(room);

        assertEquals(1, hotel.getGuests().size());
        assertEquals(1, hotel.getRooms().size());
    }
}
