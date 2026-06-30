package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReceptionistTest {

    @Test
    public void testReceptionistWork() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var r = new Receptionist("rec100","R","S", LocalDateTime.now(), "PB", "303", hotel);
        assertTrue(r.work().contains("receives"));
        assertEquals(Job.RECEPTIONIST, r.getJob());
    }

    @Test
    public void testReceptionistHoursAndDefaultSalary() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var receptionist = new Receptionist("rec101","R","S", LocalDateTime.now(), "PB", "303", hotel);

        assertEquals(8, receptionist.countWorkHour());
        assertEquals(2000, receptionist.getSalary());
    }
}
