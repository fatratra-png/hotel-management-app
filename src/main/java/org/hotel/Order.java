package org.hotel;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Order {
    private String id;
    private Guest guest;
    private Room room;
    private List<String> items;
    private OrderStatus status;
    private LocalDateTime orderedAt;
    private LocalDateTime deliveredAt;
    private Employee preparedBy;
    private Employee deliveredBy;

    public Order(String id, Guest guest, Room room, List<String> items) {
        this.id = id;
        this.guest = guest;
        this.room = room;
        this.items = items;
        this.status = OrderStatus.PLACED;
        this.orderedAt = LocalDateTime.now();
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        if (status == OrderStatus.DELIVERED) {
            this.deliveredAt = LocalDateTime.now();
        }
    }

    public void setPreparedBy(Employee cook) {
        this.preparedBy = cook;
    }

    public void setDeliveredBy(Employee server) {
        this.deliveredBy = server;
    }
}
