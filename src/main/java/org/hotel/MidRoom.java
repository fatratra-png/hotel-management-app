package org.hotel;

import lombok.Getter;

@Getter
public class MidRoom extends Room {
  public MidRoom() {
    super();
    this.hasAC = true;
    this.nightlyRate = 80.0;
  }
}
