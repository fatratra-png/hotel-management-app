package org.hotel;

import lombok.Getter;
import org.hotel.order.OrderItem;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Order {
    private String id;
    private Guest guest;
    private Room room;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime orderedAt;
    private LocalDateTime deliveredAt;
    private Employee preparedBy;
    private Employee deliveredBy;

    public Order(String id, Guest guest, Room room, List<OrderItem> items) {
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

    public Duration getElapsedTime() {
        LocalDateTime end = (deliveredAt != null) ? deliveredAt : LocalDateTime.now();
        return Duration.between(orderedAt, end);
    }

    public boolean isActive() {
        return status != OrderStatus.DELIVERED && status != OrderStatus.CANCELLED;
    }

    public void cancel() {
        if (this.status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("It is not possible to cancel an order that has already been delivered.");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void setPreparedBy(Employee cook) {
        this.preparedBy = cook;
    }

    public void setDeliveredBy(Employee server) {
        this.deliveredBy = server;
    }
}
