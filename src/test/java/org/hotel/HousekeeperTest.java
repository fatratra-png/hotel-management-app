package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class HousekeeperTest {

    @Test
    public void testHousekeeperWorkAndHours() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Housekeeper hk = new Housekeeper("hk1","HK","L", LocalDateTime.now(), "PB", "101", hotel);
        assertTrue(hk.work().contains("cleaning"));
        assertEquals(10, hk.countWorkHour());
    }

    @Test
    public void testHousekeeperHasExpectedJobAndDefaultSalary() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Housekeeper hk = new Housekeeper("hk2","HK","L", LocalDateTime.now(), "PB", "101", hotel);

        assertEquals(Job.HOUSEKEEPER, hk.getJob());
        assertEquals(2000, hk.getSalary());
    }
}
