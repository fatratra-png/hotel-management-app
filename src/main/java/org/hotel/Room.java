package org.hotel;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Room {
  private boolean hasWiFi = true;
  protected boolean isOccupied = false;
  protected boolean hasSeaView = false;
  protected boolean hasAC = false;
  protected double nightlyRate;
  private List<Order> deliveries = new ArrayList<>();

  public Room() {}

  public void receiveDelivery(Order order) {
    this.deliveries.add(order);
  }
}
