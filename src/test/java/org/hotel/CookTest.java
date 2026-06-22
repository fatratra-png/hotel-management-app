package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CookTest {

    @Test
    public void testPrepareSetsStatusAndPreparedBy() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Cook cook = new Cook("c10","Chef","A", LocalDateTime.now(), "PB", "000", hotel);
        Room room = new StandardRoom();
        Guest guest = new Guest("g10","A");
        Order order = new Order("o10", guest, room, java.util.List.of("item"));
        cook.prepare(order);
        assertEquals(OrderStatus.READY, order.getStatus());
        assertEquals(cook, order.getPreparedBy());
    }

    @Test
    public void testPrepareDoesNotMarkOrderAsDelivered() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Cook cook = new Cook("c11","Chef","A", LocalDateTime.now(), "PB", "000", hotel);
        Order order = new Order("o11", new Guest("g11","A"), new StandardRoom(), java.util.List.of("soup"));

        cook.prepare(order);

        assertEquals(OrderStatus.READY, order.getStatus());
        assertNull(order.getDeliveredAt());
        assertNull(order.getDeliveredBy());
    }

    @Test
    public void testPrepareNullOrderThrows() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Cook cook = new Cook("c12","Chef","A", LocalDateTime.now(), "PB", "000", hotel);

        assertThrows(NullPointerException.class, () -> cook.prepare(null));
    }
}
