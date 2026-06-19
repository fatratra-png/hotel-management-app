package org.hotel;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class Cook extends Employee{
    public Cook(String id, String name, String surname, LocalDateTime dateOfBirth, String placeOfBirth, String phoneNumber, Hotel hotel) {
        super(id, name, surname, dateOfBirth, Job.COOK, placeOfBirth, phoneNumber, hotel);
    }

    public void prepare(Order order) {
        order.setStatus(OrderStatus.PREPARING);
        order.setPreparedBy(this);
        order.setStatus(OrderStatus.READY);
    }

    @Override
    public String work(){
        return getName() + " is cooking in the kitchen";
    }
}
