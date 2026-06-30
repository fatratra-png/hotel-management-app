package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class GuardTest {

    @Test
    public void testGuardWorkAndHours() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var guard = new Guard("g100","Guard","X", LocalDateTime.now(), "PB", "999", hotel);
        assertTrue(guard.work().contains("guard"));
        assertEquals(12, guard.countWorkHour());
    }

    @Test
    public void testGuardHasExpectedJobAndDefaultSalary() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var guard = new Guard("g101","Guard","X", LocalDateTime.now(), "PB", "999", hotel);

        assertEquals(Job.GUARD, guard.getJob());
        assertEquals(2000, guard.getSalary());
    }
}
