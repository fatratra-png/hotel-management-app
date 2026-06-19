package origa.hotel;

import lombok.Getter;

@Getter
public class LuxuryRoom extends Room{
    public LuxuryRoom(boolean hasAC, boolean hasSeaView) {
        super(hasAC, hasSeaView);
    }
}
