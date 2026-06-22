package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAndOrderTest {

    @Test
    public void testEmployeeAddRemoveAndCounts() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,new HashSet<>());

        Cook cook = new Cook("c2","C","S",LocalDateTime.now(),"PB","000",hotel);
        Server server = new Server("s2","S","T",LocalDateTime.now(),"PB","111",hotel);
        Manager manager = new Manager("m2","M","N",LocalDateTime.now(),"PB","222",hotel);

        hotel.addEmployee(cook);
        hotel.addEmployee(server);
        hotel.addEmployee(manager);

        assertEquals(3, hotel.countEmployees());
        assertEquals(1, hotel.countEmployeesByJob(Job.COOK));
        assertEquals(1, hotel.countEmployeesByJob(Job.SERVER));

        hotel.removeEmployee("s2");
        assertEquals(2, hotel.countEmployees());
    }

    @Test
    public void testRemovingUnknownEmployeeDoesNotChangeCount() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,new HashSet<>());
        Cook cook = new Cook("c3","C","S",LocalDateTime.now(),"PB","000",hotel);
        hotel.addEmployee(cook);

        hotel.removeEmployee("missing");

        assertEquals(1, hotel.countEmployees());
        assertEquals(1, hotel.countEmployeesByJob(Job.COOK));
    }

    @Test
    public void testAddingSameEmployeeInstanceTwiceIsNotDuplicated() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,new HashSet<>());
        Cook cook = new Cook("c4","C","S",LocalDateTime.now(),"PB","000",hotel);

        hotel.addEmployee(cook);
        hotel.addEmployee(cook);

        assertEquals(1, hotel.countEmployees());
    }

    @Test
    public void testCountEmployeesByMissingJobReturnsZero() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,new HashSet<>());

        assertEquals(0, hotel.countEmployeesByJob(Job.GUARD));
    }

    @Test
    public void testOrderStatusTransitions() {
        Hotel hotel = new Hotel("H","L","A","P",4,10,null);
        Room room = new StandardRoom();
        hotel.addRoom(room);
        Guest guest = new Guest("g9","Anna");
        hotel.register(guest);
        hotel.book(guest, room);

        Order order = new Order("o9", guest, room, java.util.List.of("item1"));
        assertEquals(OrderStatus.PLACED, order.getStatus());

        Cook cook = new Cook("c9","Chef","X",LocalDateTime.now(),"PB","000",hotel);
        order.setPreparedBy(cook);
        order.setStatus(OrderStatus.PREPARING);
        assertEquals(OrderStatus.PREPARING, order.getStatus());

        order.setStatus(OrderStatus.READY);
        assertEquals(OrderStatus.READY, order.getStatus());

        Server server = new Server("s9","Serv","Y",LocalDateTime.now(),"PB","111",hotel);
        order.setDeliveredBy(server);
        order.setStatus(OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        assertNotNull(order.getDeliveredAt());
    }

    @Test
    public void testDeliveredAtIsOnlySetWhenDelivered() {
        Order order = new Order("o10", new Guest("g10","Anna"), new StandardRoom(), java.util.List.of("item1"));

        order.setStatus(OrderStatus.PREPARING);
        order.setStatus(OrderStatus.READY);

        assertNull(order.getDeliveredAt());
    }
}
