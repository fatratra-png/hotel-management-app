package org.hotel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LuxuryRoomTest {

    @Test
    public void testLuxuryRoomHasACAndSeaView() {
        var lr = new LuxuryRoom();
        assertTrue(lr.isHasAC());
        assertTrue(lr.isHasSeaView());
    }

    @Test
    public void testLuxuryRoomKeepsBaseRoomDefaults() {
        var room = new LuxuryRoom();

        assertTrue(room.isHasWiFi());
        assertFalse(room.isOccupied());
        assertTrue(room.getDeliveries().isEmpty());
    }

    @Test
    public void testLuxuryRoomCanBeMarkedOccupied() {
        var room = new LuxuryRoom();

        room.setOccupied(true);

        assertTrue(room.isOccupied());
    }
}
