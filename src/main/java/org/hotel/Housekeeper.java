package org.hotel;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Housekeeper extends Employee {

  public Housekeeper(
      String id,
      String name,
      String surname,
      LocalDateTime dateOfBirth,
      String placeOfBirth,
      String phoneNumber,
      Hotel hotel) {
    super(id, name, surname, dateOfBirth, Job.HOUSEKEEPER, placeOfBirth, phoneNumber, hotel,2000.0);
  }

  @Override
  public String work() {
    return getName() + " is cleaning rooms";
  }

  @Override
  public int countWorkHour() {
    return 10;
  }
}
