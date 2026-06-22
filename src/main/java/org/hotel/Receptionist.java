package org.hotel;

import java.time.LocalDateTime;

public class Receptionist extends Employee {
  public Receptionist(
      String id,
      String name,
      String surname,
      LocalDateTime dateOfBirth,
      String placeOfBirth,
      String phoneNumber,
      Hotel hotel) {
    super(id, name, surname, dateOfBirth, Job.RECEPTIONIST, placeOfBirth, phoneNumber, hotel,2000.0);
  }

  public String work() {
    return getName() + " respectfully receives the guests";
  }

  @Override
  public int countWorkHour() {
    return 8;
  }
}
