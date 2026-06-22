package org.hotel;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Cook extends Employee {
  public Cook(
      String id,
      String name,
      String surname,
      LocalDateTime dateOfBirth,
      String placeOfBirth,
      String phoneNumber,
      Hotel hotel) {
    super(id, name, surname, dateOfBirth, Job.COOK, placeOfBirth, phoneNumber, hotel,2000.0);
  }

  public void prepare(Order order) {
    order.setStatus(OrderStatus.PREPARING);
    order.setPreparedBy(this);
    order.setStatus(OrderStatus.READY);
  }

  @Override
  public String work() {
    return getName() + " is cooking in the kitchen";
  }

  @Override
  public int countWorkHour() {
    return 8;
  }
}
