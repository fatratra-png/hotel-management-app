package org.hotel;

import java.time.LocalDateTime;

public class Guard extends Employee {
  public Guard(
      String id,
      String name,
      String surname,
      LocalDateTime dateOfBirth,
      String placeOfBirth,
      String phoneNumber,
      Hotel hotel) {
    super(id, name, surname, dateOfBirth, Job.GUARD, placeOfBirth, phoneNumber, hotel,2000.0);
  }

  @Override
  public String work() {
    return getName() + " is a guard at " + this.getHotel();
  }

  @Override
  public int countWorkHour() {
    return 12;
  }
}
