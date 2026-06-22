package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class StaffBehaviorTest {

    @Test
    public void testReceptionistWorkAndHours() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Receptionist r = new Receptionist("rec1","R","L",LocalDateTime.now(),"PB","123",hotel);
        assertTrue(r.work().contains("receives"));
        assertEquals(8, r.countWorkHour());
    }

    @Test
    public void testHousekeeperAndGuard() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Housekeeper h = new Housekeeper("h1","H","K",LocalDateTime.now(),"PB","222",hotel);
        Guard g = new Guard("g1","G","D",LocalDateTime.now(),"PB","333",hotel);
        assertTrue(h.work().contains("cleaning"));
        assertEquals(10, h.countWorkHour());
        assertTrue(g.work().contains("guard"));
        assertEquals(12, g.countWorkHour());
    }

    @Test
    public void testCookAndServerActions() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Cook cook = new Cook("c1","Cook","C",LocalDateTime.now(),"PB","444",hotel);
        Server server = new Server("s1","Serv","S",LocalDateTime.now(),"PB","555",hotel);
        Room room = new StandardRoom();
        Guest guest = new Guest("g3","Sam");
        hotel.addRoom(room);
        hotel.register(guest);
        hotel.book(guest, room);
        Order order = new Order("ord1", guest, room, java.util.List.of("a"));
        cook.prepare(order);
        assertEquals(OrderStatus.READY, order.getStatus());
        assertEquals(cook, order.getPreparedBy());
        server.deliver(order);
        assertEquals(server, order.getDeliveredBy());
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    public void testCookAndServerHaveExpectedJobsAndHours() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Cook cook = new Cook("c2","Cook","C",LocalDateTime.now(),"PB","444",hotel);
        Server server = new Server("s2","Serv","S",LocalDateTime.now(),"PB","555",hotel);

        assertEquals(Job.COOK, cook.getJob());
        assertEquals(8, cook.countWorkHour());
        assertEquals(Job.SERVER, server.getJob());
        assertEquals(8, server.countWorkHour());
    }

    @Test
    public void testServerDeliveryAddsExactlyOneDelivery() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Server server = new Server("s3","Serv","S",LocalDateTime.now(),"PB","555",hotel);
        Room room = new StandardRoom();
        Order order = new Order("ord2", new Guest("g4","Sam"), room, java.util.List.of("a"));

        server.deliver(order);

        assertEquals(1, room.getDeliveries().size());
        assertEquals(order, room.getDeliveries().getFirst());
    }

    @Test
    public void testWorkMessagesIncludeEmployeeName() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Cook cook = new Cook("c3","CookName","C",LocalDateTime.now(),"PB","444",hotel);
        Server server = new Server("s4","ServerName","S",LocalDateTime.now(),"PB","555",hotel);

        assertTrue(cook.work().startsWith("CookName"));
        assertTrue(server.work().startsWith("ServerName"));
    }
}
