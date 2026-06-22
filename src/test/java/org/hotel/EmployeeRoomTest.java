package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRoomTest {

    @Test
    public void testEmployeeSalaryCalculationsAndWork() {
        Hotel hotel = new Hotel("H","L","A","P",5,10,null);
        Manager m = new Manager("m1","M","N",LocalDateTime.now(),"PB","000",hotel);
        assertEquals(8, m.countWorkHour());
        assertTrue(m.work().contains("manager"));

        Guard g = new Guard("g1","G","S",LocalDateTime.now(),"PB","111",hotel);
        assertEquals(12, g.countWorkHour());

        m.addBonus(100);
        double salary = m.countSalaryByHour(10,8);
        assertEquals(80, salary);
        double overtime = m.calculateOvertimePay(10,2);
        assertEquals(30, overtime);
        double night = m.calculateNightDifferential(10,2);
        assertEquals(25, night);
        double deductions = m.calculateDeductions(10);
        assertEquals(m.getSalary() * 0.10, deductions);
    }

    @Test
    public void testSalaryCalculationWithZeroHours() {
        Hotel hotel = new Hotel("H","L","A","P",5,10,null);
        Manager manager = new Manager("m2","M","N",LocalDateTime.now(),"PB","000",hotel);

        assertEquals(0, manager.countSalaryByHour(10, 0));
        assertEquals(0, manager.calculateDeductions(15));
    }

    @Test
    public void testNetSalaryWithFullDeductionRate() {
        Hotel hotel = new Hotel("H","L","A","P",5,10,null);
        Manager manager = new Manager("m3","M","N",LocalDateTime.now(),"PB","000",hotel);
        manager.countSalaryByHour(25, 4);

        assertEquals(0, manager.calculateNetSalary(100));
    }

    @Test
    public void testRoomPropertiesAndDeliveryReceive() {
        StandardRoom s = new StandardRoom();
        MidRoom mid = new MidRoom();
        LuxuryRoom lux = new LuxuryRoom();
        assertTrue(s.isHasWiFi());
        assertFalse(s.isOccupied());
        assertTrue(mid.isHasAC());
        assertTrue(lux.isHasSeaView());

        Room r = new StandardRoom();
        Guest guest = new Guest("g2","Bob");
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        hotel.addRoom(r);
        hotel.register(guest);
        hotel.book(guest, r);

        Order o = new Order("id", guest, r, java.util.List.of("x"));
        r.receiveDelivery(o);
        assertTrue(r.getDeliveries().contains(o));
    }

    @Test
    public void testRoomCanReceiveMultipleDeliveriesInOrder() {
        Room room = new StandardRoom();
        Guest guest = new Guest("g3","Bob");
        Order first = new Order("first", guest, room, java.util.List.of("water"));
        Order second = new Order("second", guest, room, java.util.List.of("tea"));

        room.receiveDelivery(first);
        room.receiveDelivery(second);

        assertEquals(java.util.List.of(first, second), room.getDeliveries());
    }
}
