package org.hotel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hotel.order.OrderItem;
import org.hotel.payment.Invoice;
import org.hotel.payment.Payment;
import org.hotel.payment.PaymentMethod;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

@EqualsAndHashCode
@Getter

public class Hotel {
    private final String name;
    private final String location;
    private final String address;
    private final String phoneNumber;
    private final int starCount;
    private final int roomCount;
    private final Set<Employee> employees;
    private final Set<Guest> guests;
    private final Set<Room> rooms;
    private final List<Reservation> reservations;
    private final List<Order> orders;
    private int orderIdCounter;

    public Hotel(String name, String location, String address, String phoneNumber, int starCount, int roomCount) {
        if(starCount < 0 || starCount > 5) throw new IllegalArgumentException("Star count must be between 0 and 5");
        this.name = name;
        this.location = location;
        this.address = address;
        this.starCount = starCount;
        this.roomCount = roomCount;
        this.phoneNumber = phoneNumber;
        this.employees = new HashSet<>();
        this.guests = new HashSet<>();
        this.rooms = new HashSet<>();
        this.reservations = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void register(Guest guest) {
        guests.add(guest);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void reserveRoom(Guest guest, Room room) {
        reserveRoom(guest, room, LocalDate.now(), LocalDate.now().plusDays(1));
    }

    public void reserveRoom(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        if (!isRoomAvailable(room, checkIn, checkOut)) {
            throw new IllegalStateException("Room is not available for the selected dates");
        }
        room.setOccupied(true);
        var reservation = new Reservation(checkIn, checkOut, guest, room);
        reservations.add(reservation);
        guest.book(room);
    }

    public boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        for (var r : reservations) {
            if (r.getRoom().equals(room) && r.overlaps(checkIn, checkOut)) {
                return false;
            }
        }
        return true;
    }

    public Order commandToRoom(Guest guest, List<OrderItem> items) {
        var room = getRoomOf(guest);
        if (room == null) {
            throw new IllegalStateException("Guest has no room reservation");
        }
        var orderId = String.valueOf(++orderIdCounter);
        var order = new Order(orderId, guest, room, items);
        orders.add(order);
        return order;
    }

    public void deliverToRoom(Order order) {
        if (!order.isActive() || order.getStatus() != OrderStatus.PLACED) {
            throw new IllegalStateException("Order cannot be delivered from current status: " + order.getStatus());
        }
        Cook cook = findAvailableCook();
        if (cook != null) {
            cook.prepare(order);
        }
        Waiter waiter = findAvailableWaiter();
        if (waiter != null) {
            waiter.deliver(order);
        }
        addOrderChargesToInvoice(order);
    }

    private void addOrderChargesToInvoice(Order order) {
        for (var r : reservations) {
            if (r.getGuest().equals(order.getGuest())) {
                r.getInvoice().addOrderCharges(order);
                break;
            }
        }
    }

    public List<Room> getAvailableRooms() {
        var available = new ArrayList<Room>();
        for (var room : rooms) {
            if (!room.isOccupied()) {
                available.add(room);
            }
        }
        return available;
    }

    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        var available = new ArrayList<Room>();
        for (var room : rooms) {
            if (isRoomAvailable(room, checkIn, checkOut)) {
                available.add(room);
            }
        }
        return available;
    }

    public Room getRoomOf(Guest guest) {
        for (var r : reservations) {
            if (r.getGuest().equals(guest)) {
                return r.getRoom();
            }
        }
        return null;
    }

    public Reservation getReservationOf(Guest guest) {
        for (var r : reservations) {
            if (r.getGuest().equals(guest)) {
                return r;
            }
        }
        return null;
    }

    public Invoice getInvoiceFor(Guest guest) {
        var reservation = getReservationOf(guest);
        return reservation != null ? reservation.getInvoice() : null;
    }

    public Payment payInvoice(Guest guest, double amount, PaymentMethod method) {
        var invoice = getInvoiceFor(guest);
        if (invoice == null) {
            throw new IllegalStateException("Guest has no invoice");
        }
        return invoice.pay(amount, method);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == status) {
                result.add(order);
            }
        }
        return result;
    }

    private Cook findAvailableCook() {
        for (Employee emp : employees) {
            if (emp instanceof Cook) {
                return (Cook) emp;
            }
        }
        return null;
    }

    private Waiter findAvailableWaiter() {
        for (Employee emp : employees) {
            if (emp instanceof Waiter) {
                return (Waiter) emp;
            }
        }
        return null;
    }

    public void book(Guest guest, Room room){
        reserveRoom(guest, room);
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    public void removeEmployee(String id){
        employees.removeIf(e -> e.getId().equals(id));
    }

    public int countEmployees(){
        return employees.size();
    }

    public int countEmployeesByJob(Job job){
        int count = 0;
        for(Employee employee : employees){
            if(employee.getJob() == job){
                count++;
            }
        }
        return count;
    }
}
