package org.hotel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StandardRoomTest {

    @Test
    public void testStandardRoomDefaults() {
        StandardRoom sr = new StandardRoom();
        assertTrue(sr.isHasWiFi());
        assertFalse(sr.isHasAC());
        assertFalse(sr.isHasSeaView());
    }

    @Test
    public void testStandardRoomStartsUnoccupiedWithNoDeliveries() {
        StandardRoom room = new StandardRoom();

        assertFalse(room.isOccupied());
        assertTrue(room.getDeliveries().isEmpty());
    }

    @Test
    public void testStandardRoomSettersCanChangeMutableProperties() {
        StandardRoom room = new StandardRoom();

        room.setOccupied(true);
        room.setHasWiFi(false);

        assertTrue(room.isOccupied());
        assertFalse(room.isHasWiFi());
    }
}
