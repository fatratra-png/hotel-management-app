package org.hotel;

import lombok.Getter;
import org.hotel.payment.Invoice;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
public class Reservation {
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final Guest guest;
    private final Room room;
    private final Invoice invoice;

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
        this.invoice = new Invoice("INV-" + guest.getId() + "-" + checkIn, this);
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        this.invoice.addRoomCharges(nights * room.getNightlyRate());
    }

    public long getNights() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public boolean overlaps(LocalDate from, LocalDate to) {
        return checkIn.isBefore(to) && checkOut.isAfter(from);
    }
}
