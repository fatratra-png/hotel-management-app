package org.hotel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@EqualsAndHashCode
@Getter

public class Hotel {
    private final String name;
    private final String location;
    private final String address;
    private final String phoneNumber;
    private int starCount;
    private final int roomCount;
    private final Set<Employee> employees;
    private final Set<Guest> guests;
    private final Set<Room> rooms;
    private final Map<Guest, Room> reservations;
    private final List<Order> orders;

    public Hotel(String name, String location, String address, String phoneNumber, int starCount, int roomCount, Set<Employee> employees) {
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
        this.reservations = new HashMap<>();
        this.orders = new ArrayList<>();
    }

    public void register(Guest guest) {
        guests.add(guest);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void reserveRoom(Guest guest, Room room) {
        if (room.isOccupied()) {
            throw new IllegalStateException("Room is already occupied");
        }
        room.setOccupied(true);
        reservations.put(guest, room);
        guest.book(room);
    }

    public Order commandToRoom(Guest guest, List<String> items) {
        Room room = reservations.get(guest);
        if (room == null) {
            throw new IllegalStateException("Guest has no room reservation");
        }
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        Order order = new Order(orderId, guest, room, items);
        orders.add(order);
        return order;
    }

    public void deliverToRoom(Order order) {
        findAvailableCook().ifPresent(cook -> cook.prepare(order));
        findAvailableServer().ifPresent(server -> server.deliver(order));
    }

    public List<Room> getAvailableRooms() {
        return rooms.stream()
                .filter(room -> !room.isOccupied())
                .collect(Collectors.toList());
    }

    public Room getRoomOf(Guest guest) {
        return reservations.get(guest);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orders.stream()
                .filter(order -> order.getStatus() == status)
                .collect(Collectors.toList());
    }

    public java.util.Optional<Cook> findAvailableCook() {
        return employees.stream()
                .filter(emp -> emp instanceof Cook)
                .map(emp -> (Cook) emp)
                .findFirst();
    }

    public java.util.Optional<Server> findAvailableServer() {
        return employees.stream()
                .filter(emp -> emp instanceof Server)
                .map(emp -> (Server) emp)
                .findFirst();
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
        return (int) employees.stream()
                .filter(employee -> employee.getJob() == job)
                .count();
    }
}