package org.hotel;

import lombok.Getter;

@Getter
public class LuxuryRoom extends Room {
  public LuxuryRoom() {
    super();
    this.hasAC = true;
    this.hasSeaView = true;
  }
}
