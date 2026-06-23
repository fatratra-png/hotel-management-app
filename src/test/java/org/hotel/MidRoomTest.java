package org.hotel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MidRoomTest {

    @Test
    public void testMidRoomHasAC() {
        var mr = new MidRoom();
        assertTrue(mr.isHasAC());
        assertFalse(mr.isHasSeaView());
    }

    @Test
    public void testMidRoomKeepsBaseRoomDefaults() {
        var room = new MidRoom();

        assertTrue(room.isHasWiFi());
        assertFalse(room.isOccupied());
        assertTrue(room.getDeliveries().isEmpty());
    }

    @Test
    public void testMidRoomCanBeMarkedOccupied() {
        var room = new MidRoom();

        room.setOccupied(true);

        assertTrue(room.isOccupied());
    }
}
