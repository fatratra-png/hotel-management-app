package origa.hotel;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class Server extends Employee{
    public Server(String id, String name, String surname, LocalDateTime dateOfBirth, Job job, String placeOfBirth, String phoneNumber, Hotel hotel) {
        super(id, name, surname, dateOfBirth, job, placeOfBirth, phoneNumber, hotel);
    }

    public void deliverFood(){

    }
    @Override
    public String work() {
        deliverFood();
        return "";
    }
}
