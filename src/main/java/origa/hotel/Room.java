package origa.hotel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Room {
    private boolean hasWiFi = true;
    private boolean isOccupied = false;
    private boolean hasSeaView = false;
    private boolean hasAC = false;

    public Room(boolean hasAC) {
        this.hasAC = hasAC;
    }

    public Room(boolean hasAC, boolean hasSeaView){
        this.hasAC = hasAC;
        this.hasSeaView = hasSeaView;
    }
}
