package origa.hotel;

import java.time.LocalDateTime;

public class Guard extends Employee{
    public Guard(String id, String name, String surname, LocalDateTime dateOfBirth, Job job, String placeOfBirth, String phoneNumber, Hotel hotel) {
        super(id, name, surname, dateOfBirth, job, placeOfBirth, phoneNumber, hotel);
    }

    @Override
    public String work() {
        return getName() + " is a guard at " + this.getHotel();
    }
}
