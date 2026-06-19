package origa.hotel;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Guest {
    private String id;
    private String name;
    private List<Room> bookedRooms;
    private List<Order> orders;

    public Guest(String id, String name) {
        this.id = id;
        this.name = name;
        this.bookedRooms = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void book(Room room) {
        bookedRooms.add(room);
    }

    public Order placeOrder(String orderId, List<String> items, Hotel hotel) {
        Order order = hotel.commandToRoom(this, items);
        orders.add(order);
        return order;
    }
}
