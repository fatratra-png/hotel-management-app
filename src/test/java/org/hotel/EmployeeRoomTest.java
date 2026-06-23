package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRoomTest {

    @Test
    public void testEmployeeSalaryCalculationsAndWork() {
        var hotel = new Hotel("H","L","A","P",5,10,null);
        var m = new Manager("m1","M","N",LocalDateTime.now(),"PB","000",hotel);
        assertEquals(8, m.countWorkHour());
        assertTrue(m.work().contains("manager"));

        var g = new Guard("g1","G","S",LocalDateTime.now(),"PB","111",hotel);
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
        var hotel = new Hotel("H","L","A","P",5,10,null);
        var manager = new Manager("m2","M","N",LocalDateTime.now(),"PB","000",hotel);

        assertEquals(0, manager.countSalaryByHour(10, 0));
        assertEquals(0, manager.calculateDeductions(15));
    }

    @Test
    public void testNetSalaryWithFullDeductionRate() {
        var hotel = new Hotel("H","L","A","P",5,10,null);
        var manager = new Manager("m3","M","N",LocalDateTime.now(),"PB","000",hotel);
        manager.countSalaryByHour(25, 4);

        assertEquals(0, manager.calculateNetSalary(100));
    }

    @Test
    public void testRoomPropertiesAndDeliveryReceive() {
        var s = new StandardRoom();
        var mid = new MidRoom();
        var lux = new LuxuryRoom();
        assertTrue(s.isHasWiFi());
        assertFalse(s.isOccupied());
        assertTrue(mid.isHasAC());
        assertTrue(lux.isHasSeaView());

        var r = new StandardRoom();
        var guest = new Guest("g2","Bob");
        var hotel = new Hotel("H","L","A","P",4,5,null);
        hotel.addRoom(r);
        hotel.register(guest);
        hotel.book(guest, r);

        var o = new Order("id", guest, r, java.util.List.of("x"));
        r.receiveDelivery(o);
        assertTrue(r.getDeliveries().contains(o));
    }

    @Test
    public void testRoomCanReceiveMultipleDeliveriesInOrder() {
        var room = new StandardRoom();
        var guest = new Guest("g3","Bob");
        var first = new Order("first", guest, room, java.util.List.of("water"));
        var second = new Order("second", guest, room, java.util.List.of("tea"));

        room.receiveDelivery(first);
        room.receiveDelivery(second);

        assertEquals(java.util.List.of(first, second), room.getDeliveries());
    }
}
