package org.hotel;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HotelTest {

    @Test
    public void testReserveAndOrderAndDeliverFlow() {
        var hotel = new Hotel("H", "L", "A", "P", 4, 10, null);
        var room = new StandardRoom();
        hotel.addRoom(room);

        var guest = new Guest("g1", "Alice");
        hotel.register(guest);

        hotel.book(guest, room);
        assertTrue(room.isOccupied());
        assertTrue(guest.getBookedRooms().contains(room));

        Order order = guest.placeOrder("o1", List.of("coffee", "sandwich"), hotel);
        assertNotNull(order);
        assertEquals(OrderStatus.PLACED, order.getStatus());

        var cook = new Cook("c1", "Cooky", "S", LocalDateTime.now(), "PB", "000", hotel);
        var waiter = new Waiter("s1", "Serve", "T", LocalDateTime.now(), "PB", "111", hotel);
        hotel.addEmployee(cook);
        hotel.addEmployee(waiter);

        hotel.deliverToRoom(order);

        assertEquals(cook, order.getPreparedBy());
        assertEquals(waiter, order.getDeliveredBy());
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
        var zeroStar = new Hotel("H","L","A","P", 0, 5, null);
        var fiveStar = new Hotel("H","L","A","P", 5, 5, null);

        assertEquals(0, zeroStar.getStarCount());
        assertEquals(5, fiveStar.getStarCount());
    }

    @Test
    public void testReserveOccupiedRoomThrowsAndDoesNotBookSecondGuest() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var room = new StandardRoom();
        var first = new Guest("g1", "Alice");
        var second = new Guest("g2", "Bob");
        hotel.reserveRoom(first, room);

        assertThrows(IllegalStateException.class, () -> hotel.reserveRoom(second, room));
        assertTrue(first.getBookedRooms().contains(room));
        assertFalse(second.getBookedRooms().contains(room));
    }

    @Test
    public void testCommandToRoomWithoutReservationThrows() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var guest = new Guest("g1", "Alice");

        assertThrows(IllegalStateException.class, () -> hotel.commandToRoom(guest, List.of("coffee")));
        assertTrue(hotel.getOrders().isEmpty());
    }

    @Test
    public void testCommandToRoomStoresOrderWithGeneratedEightCharacterId() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var room = new StandardRoom();
        var guest = new Guest("g1", "Alice");
        hotel.reserveRoom(guest, room);

        Order order = hotel.commandToRoom(guest, List.of("coffee"));

        assertFalse(order.getId().isEmpty());
        assertTrue(hotel.getOrders().contains(order));
        assertEquals(List.of("coffee"), order.getItems());
    }

    @Test
    public void testDeliverToRoomWithoutStaffLeavesOrderPlaced() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var room = new StandardRoom();
        var guest = new Guest("g1", "Alice");
        var order = new Order("o1", guest, room, List.of("coffee"));

        hotel.deliverToRoom(order);

        assertEquals(OrderStatus.PLACED, order.getStatus());
        assertNull(order.getPreparedBy());
        assertNull(order.getDeliveredBy());
        assertTrue(room.getDeliveries().isEmpty());
    }

    @Test
    public void testDeliverToRoomWithOnlyCookPreparesButDoesNotDeliver() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var cook = new Cook("c1", "Cooky", "S", LocalDateTime.now(), "PB", "000", hotel);
        var order = new Order("o1", new Guest("g1", "Alice"), new StandardRoom(), List.of("coffee"));
        hotel.addEmployee(cook);

        hotel.deliverToRoom(order);

        assertEquals(OrderStatus.READY, order.getStatus());
        assertEquals(cook, order.getPreparedBy());
        assertNull(order.getDeliveredBy());
    }

    @Test
    public void testDeliverToRoomWithOnlyServerDeliversWithoutPreparedBy() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var waiter = new Waiter("s1", "Serve", "T", LocalDateTime.now(), "PB", "111", hotel);
        var room = new StandardRoom();
        var order = new Order("o1", new Guest("g1", "Alice"), room, List.of("coffee"));
        hotel.addEmployee(waiter);

        hotel.deliverToRoom(order);

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        assertNull(order.getPreparedBy());
        assertEquals(waiter, order.getDeliveredBy());
        assertTrue(room.getDeliveries().contains(order));
    }

    @Test
    public void testRegisterAndAddRoomStoreUniqueInstances() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var guest = new Guest("g1", "Alice");
        var room = new StandardRoom();

        hotel.register(guest);
        hotel.register(guest);
        hotel.addRoom(room);
        hotel.addRoom(room);

        assertEquals(1, hotel.getGuests().size());
        assertEquals(1, hotel.getRooms().size());
    }

    @Test
    public void testGetAvailableRoomsExcludesOccupiedRooms() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var available = new StandardRoom();
        var occupied = new StandardRoom();
        hotel.addRoom(available);
        hotel.addRoom(occupied);

        var guest = new Guest("g1", "Alice");
        hotel.reserveRoom(guest, occupied);

        List<Room> result = hotel.getAvailableRooms();

        assertTrue(result.contains(available));
        assertFalse(result.contains(occupied));
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAvailableRoomsReturnsEmptyWhenAllOccupied() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var room = new StandardRoom();
        hotel.addRoom(room);
        hotel.reserveRoom(new Guest("g1", "Alice"), room);

        assertTrue(hotel.getAvailableRooms().isEmpty());
    }

    @Test
    public void testGetAvailableRoomsReturnsEmptyWhenNoRoomsAdded() {
        var hotel = new Hotel("H","L","A","P",4,10,null);

        assertTrue(hotel.getAvailableRooms().isEmpty());
    }

    @Test
    public void testGetRoomOfReturnsReservedRoom() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var room = new StandardRoom();
        var guest = new Guest("g1", "Alice");
        hotel.reserveRoom(guest, room);

        assertEquals(room, hotel.getRoomOf(guest));
    }

    @Test
    public void testGetRoomOfReturnsNullWhenGuestHasNoReservation() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var guest = new Guest("g1", "Alice");

        assertNull(hotel.getRoomOf(guest));
    }

    @Test
    public void testGetOrdersByStatusReturnsOnlyMatchingOrders() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var room = new StandardRoom();
        var guest = new Guest("g1", "Alice");
        hotel.reserveRoom(guest, room);

        Order placedOrder = hotel.commandToRoom(guest, List.of("coffee"));
        Order anotherOrder = hotel.commandToRoom(guest, List.of("tea"));
        anotherOrder.setStatus(OrderStatus.DELIVERED);

        List<Order> placed = hotel.getOrdersByStatus(OrderStatus.PLACED);
        List<Order> delivered = hotel.getOrdersByStatus(OrderStatus.DELIVERED);

        assertEquals(1, placed.size());
        assertTrue(placed.contains(placedOrder));
        assertEquals(1, delivered.size());
        assertTrue(delivered.contains(anotherOrder));
    }

    @Test
    public void testGetOrdersByStatusReturnsEmptyWhenNoneMatch() {
        var hotel = new Hotel("H","L","A","P",4,10,null);
        var room = new StandardRoom();
        var guest = new Guest("g1", "Alice");
        hotel.reserveRoom(guest, room);
        hotel.commandToRoom(guest, List.of("coffee"));

        List<Order> cancelled = hotel.getOrdersByStatus(OrderStatus.CANCELLED);

        assertTrue(cancelled.isEmpty());
    }

    @Test
    public void testGetOrdersByStatusReturnsEmptyWhenNoOrdersExist() {
        var hotel = new Hotel("H","L","A","P",4,10,null);

        assertTrue(hotel.getOrdersByStatus(OrderStatus.PLACED).isEmpty());
    }

    @Test
    public void testReserveRoomWithDatesSuccess() {
        var hotel = new Hotel("H", "L", "A", "P", 4, 10, null);
        var room = new StandardRoom();
        hotel.addRoom(room);
        var guest = new Guest("g1", "Alice");
        hotel.register(guest);

        hotel.reserveRoom(guest, room, LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4));

        assertTrue(room.isOccupied());
        assertTrue(guest.getBookedRooms().contains(room));
        assertEquals(1, hotel.getReservations().size());
        assertEquals(guest, hotel.getReservations().getFirst().getGuest());
        assertEquals(room, hotel.getReservations().getFirst().getRoom());
    }

    @Test
    public void testReserveRoomOverlappingDatesThrows() {
        var hotel = new Hotel("H", "L", "A", "P", 4, 10, null);
        var room = new StandardRoom();
        hotel.addRoom(room);
        var alice = new Guest("g1", "Alice");
        var bob = new Guest("g2", "Bob");
        hotel.register(alice);
        hotel.register(bob);

        hotel.reserveRoom(alice, room, LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4));

        assertThrows(IllegalStateException.class,
            () -> hotel.reserveRoom(bob, room, LocalDate.of(2026, 7, 2), LocalDate.of(2026, 7, 5)));
        assertFalse(bob.getBookedRooms().contains(room));
    }

    @Test
    public void testReserveRoomNonOverlappingDatesAtBoundary() {
        var hotel = new Hotel("H", "L", "A", "P", 4, 10, null);
        var room = new StandardRoom();
        hotel.addRoom(room);
        var alice = new Guest("g1", "Alice");
        var bob = new Guest("g2", "Bob");
        hotel.register(alice);
        hotel.register(bob);

        hotel.reserveRoom(alice, room, LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4));
        hotel.reserveRoom(bob, room, LocalDate.of(2026, 7, 4), LocalDate.of(2026, 7, 7));

        assertEquals(2, hotel.getReservations().size());
    }

    @Test
    public void testIsRoomAvailableWithDates() {
        var hotel = new Hotel("H", "L", "A", "P", 4, 10, null);
        var room = new StandardRoom();
        hotel.addRoom(room);
        var guest = new Guest("g1", "Alice");
        hotel.register(guest);

        hotel.reserveRoom(guest, room, LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4));

        assertFalse(hotel.isRoomAvailable(room, LocalDate.of(2026, 7, 2), LocalDate.of(2026, 7, 5)));
        assertTrue(hotel.isRoomAvailable(room, LocalDate.of(2026, 7, 4), LocalDate.of(2026, 7, 7)));
        assertTrue(hotel.isRoomAvailable(new StandardRoom(), LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4)));
    }

    @Test
    public void testGetAvailableRoomsWithDates() {
        var hotel = new Hotel("H", "L", "A", "P", 4, 10, null);
        var room1 = new StandardRoom();
        var room2 = new StandardRoom();
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        var guest = new Guest("g1", "Alice");
        hotel.register(guest);

        hotel.reserveRoom(guest, room1, LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4));

        var available = hotel.getAvailableRooms(LocalDate.of(2026, 7, 2), LocalDate.of(2026, 7, 5));
        assertFalse(available.contains(room1));
        assertTrue(available.contains(room2));
        assertEquals(1, available.size());
    }

    @Test
    public void testReserveRoomWithNullDatesThrows() {
        var hotel = new Hotel("H", "L", "A", "P", 4, 10, null);
        var room = new StandardRoom();
        var guest = new Guest("g1", "Alice");

        assertThrows(IllegalArgumentException.class,
            () -> hotel.reserveRoom(guest, room, null, LocalDate.of(2026, 7, 4)));
    }

    @Test
    public void testReserveRoomWithInvalidDateOrderThrows() {
        var hotel = new Hotel("H", "L", "A", "P", 4, 10, null);
        var room = new StandardRoom();
        hotel.addRoom(room);
        var guest = new Guest("g1", "Alice");
        hotel.register(guest);

        assertThrows(IllegalArgumentException.class,
            () -> hotel.reserveRoom(guest, room, LocalDate.of(2026, 7, 4), LocalDate.of(2026, 7, 1)));
    }
}
