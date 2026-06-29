package org.hotel;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class Reservation {
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final Guest guest;
    private final Room room;

    public Reservation(LocalDate checkIn, LocalDate checkOut, Guest guest, Room room) {
        if (checkIn == null || checkOut == null || guest == null || room == null) {
            throw new IllegalArgumentException("No field can be null");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guest = guest;
        this.room = room;
    }

    public boolean overlaps(LocalDate from, LocalDate to) {
        return checkIn.isBefore(to) && checkOut.isAfter(from);
    }
}
