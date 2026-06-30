package org.hotel;

import org.junit.jupiter.api.Test;
import org.hotel.order.OrderItem;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CookTest {

    @Test
    public void testPrepareSetsStatusAndPreparedBy() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var cook = new Cook("c10","Chef","A", LocalDateTime.now(), "PB", "000", hotel);
        var room = new StandardRoom();
        var guest = new Guest("g10","A");
        var order = new Order("o10", guest, room, java.util.List.of(new OrderItem("item", 5.0)));
        cook.prepare(order);
        assertEquals(OrderStatus.READY, order.getStatus());
        assertEquals(cook, order.getPreparedBy());
    }

    @Test
    public void testPrepareDoesNotMarkOrderAsDelivered() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var cook = new Cook("c11","Chef","A", LocalDateTime.now(), "PB", "000", hotel);
        var order = new Order("o11", new Guest("g11","A"), new StandardRoom(), java.util.List.of(new OrderItem("soup", 5.0)));

        cook.prepare(order);

        assertEquals(OrderStatus.READY, order.getStatus());
        assertNull(order.getDeliveredAt());
        assertNull(order.getDeliveredBy());
    }

    @Test
    public void testPrepareNullOrderThrows() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var cook = new Cook("c12","Chef","A", LocalDateTime.now(), "PB", "000", hotel);

        assertThrows(NullPointerException.class, () -> cook.prepare(null));
    }
}
