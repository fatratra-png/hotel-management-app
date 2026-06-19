package origa.hotel;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class Room {
    private boolean hasWiFi = true;
    private boolean isOccupied = false;
    private boolean hasSeaView = false;
    private boolean hasAC = false;
    private List<Order> deliveries;

    public Room() {
        this.deliveries = new ArrayList<>();
    }

    public Room(boolean hasAC) {
        this.hasAC = hasAC;
        this.deliveries = new ArrayList<>();
    }

    public Room(boolean hasAC, boolean hasSeaView){
        this.hasAC = hasAC;
        this.hasSeaView = hasSeaView;
        this.deliveries = new ArrayList<>();
    }

    public void receiveDelivery(Order order) {
        this.deliveries.add(order);
    }
}
