package org.hotel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StandardRoomTest {

    @Test
    public void testStandardRoomDefaults() {
        var sr = new StandardRoom();
        assertTrue(sr.isHasWiFi());
        assertFalse(sr.isHasAC());
        assertFalse(sr.isHasSeaView());
    }

    @Test
    public void testStandardRoomStartsUnoccupiedWithNoDeliveries() {
        var room = new StandardRoom();

        assertFalse(room.isOccupied());
        assertTrue(room.getDeliveries().isEmpty());
    }

    @Test
    public void testStandardRoomSettersCanChangeMutableProperties() {
        var room = new StandardRoom();

        room.setOccupied(true);
        room.setHasWiFi(false);

        assertTrue(room.isOccupied());
        assertFalse(room.isHasWiFi());
    }
}
