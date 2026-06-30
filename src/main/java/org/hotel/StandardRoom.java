package org.hotel;

import lombok.Getter;

@Getter
public class StandardRoom extends Room {
  public StandardRoom() {
    super();
    this.nightlyRate = 50.0;
  }
}
