package origa.hotel;

import java.time.LocalDateTime;

public class Manager extends Employee{
    public Manager(String id, String name, String surname, LocalDateTime dateOfBirth, Job job, String placeOfBirth, String phoneNumber, Hotel hotel) {
        super(id, name, surname, dateOfBirth, job, placeOfBirth, phoneNumber, hotel);
    }

    @Override
    public String work(){
        return getName() + " is the manager at " + this.getHotel();
    }
}
