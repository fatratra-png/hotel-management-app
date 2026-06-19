package origa.hotel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter

public abstract class Employee {
    private String id;
    private String name;
    private String surname;
    private LocalDateTime dateOfBirth;
    private Job job;
    private String placeOfBirth;
    private String phoneNumber;
    private Hotel hotel;

    public abstract String work();
}
