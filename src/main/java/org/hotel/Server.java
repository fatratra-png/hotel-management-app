package org.hotel;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Server extends Employee {
  public Server(
      String id,
      String name,
      String surname,
      LocalDateTime dateOfBirth,
      String placeOfBirth,
      String phoneNumber,
      Hotel hotel) {
    super(id, name, surname, dateOfBirth, Job.SERVER, placeOfBirth, phoneNumber, hotel);
  }

  public void deliver(Order order) {
    order.setDeliveredBy(this);
    order.getRoom().receiveDelivery(order);
    order.setStatus(OrderStatus.DELIVERED);
  }

  @Override
  public String work() {
    return getName() + " is serving guests";
  }

  @Override
  public int countWorkHour() {
    return 8;
  }
}
