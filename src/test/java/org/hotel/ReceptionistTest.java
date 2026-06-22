package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReceptionistTest {

    @Test
    public void testReceptionistWork() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Receptionist r = new Receptionist("rec100","R","S", LocalDateTime.now(), "PB", "303", hotel);
        assertTrue(r.work().contains("receives"));
        assertEquals(Job.RECEPTIONIST, r.getJob());
    }

    @Test
    public void testReceptionistHoursAndDefaultSalary() {
        Hotel hotel = new Hotel("H","L","A","P",4,5,null);
        Receptionist receptionist = new Receptionist("rec101","R","S", LocalDateTime.now(), "PB", "303", hotel);

        assertEquals(8, receptionist.countWorkHour());
        assertEquals(2000, receptionist.getSalary());
    }
}
