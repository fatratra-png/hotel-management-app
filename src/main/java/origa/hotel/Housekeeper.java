package origa.hotel;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class Housekeeper extends Employee {

    public Housekeeper(String id, String name, String surname, LocalDateTime dateOfBirth, String placeOfBirth, String phoneNumber, Hotel hotel) {
        super(id, name, surname, dateOfBirth, Job.HOUSEKEEPER, placeOfBirth, phoneNumber, hotel);
    }

    @Override
    public String work(){
            return getName() + " is cleaning rooms";
    }
}
