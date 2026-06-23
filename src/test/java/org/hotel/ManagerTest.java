package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {

    @Test
    public void testManagerWorkAndJob() {
        var hotel = new Hotel("H","L","A","P",5,5,null);
        var m = new Manager("m100","M","X", LocalDateTime.now(), "PB", "202", hotel);
        assertEquals(8, m.countWorkHour());
        assertTrue(m.work().contains("manager"));
        assertEquals(Job.MANAGER, m.getJob());
    }

    @Test
    public void testManagerHasDefaultSalaryAndHotelReference() {
        var hotel = new Hotel("H","L","A","P",5,5,null);
        var manager = new Manager("m101","M","X", LocalDateTime.now(), "PB", "202", hotel);

        assertEquals(2000, manager.getSalary());
        assertEquals(hotel, manager.getHotel());
    }
}
