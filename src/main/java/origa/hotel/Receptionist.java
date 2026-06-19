package origa.hotel;

import java.time.LocalDateTime;

public class Receptionist extends Employee{
    public Receptionist(String id, String name, String surname, LocalDateTime dateOfBirth, Job job, String placeOfBirth, String phoneNumber, Hotel hotel) {
        super(id, name, surname, dateOfBirth, job, placeOfBirth, phoneNumber, hotel);
    }
    public String work(){
        return getName() + " respectfully receives the guests";
    }
}
