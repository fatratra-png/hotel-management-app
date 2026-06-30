package org.hotel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnumTest {

    @Test
    public void testJobContainsValues() {
        Job[] values = Job.values();
        assertTrue(values.length >= 1);
        boolean foundManager = false;
        for (Job j : values) if (j == Job.MANAGER) foundManager = true;
        assertTrue(foundManager);
    }

    @Test
    public void testJobContainsAllExpectedValuesInOrder() {
        assertArrayEquals(
                new Job[]{Job.MANAGER, Job.HOUSEKEEPER, Job.GUARD, Job.COOK, Job.RECEPTIONIST, Job.WAITER},
                Job.values());
    }

    @Test
    public void testOrderStatusContainsAllExpectedValuesInOrder() {
        assertArrayEquals(
                new OrderStatus[]{OrderStatus.PLACED, OrderStatus.PREPARING, OrderStatus.READY, OrderStatus.DELIVERED, OrderStatus.CANCELLED},
                OrderStatus.values());
    }

    @Test
    public void testEnumValueOfRejectsUnknownValue() {
        assertThrows(IllegalArgumentException.class, () -> Job.valueOf("BARTENDER"));
        assertThrows(IllegalArgumentException.class, () -> OrderStatus.valueOf("UNKNOWN"));
    }
}
