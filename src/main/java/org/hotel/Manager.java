package org.hotel;

import java.time.LocalDateTime;

public class Manager extends Employee {
  public Manager(
      String id,
      String name,
      String surname,
      LocalDateTime dateOfBirth,
      String placeOfBirth,
      String phoneNumber,
      Hotel hotel) {
    super(id, name, surname, dateOfBirth, Job.MANAGER, placeOfBirth, phoneNumber, hotel,2000.0);
  }

  @Override
  public String work() {
    return getName() + " is the manager at " + this.getHotel().getName();
  }

  @Override
  public int countWorkHour() {
    return 8;
  }
}
