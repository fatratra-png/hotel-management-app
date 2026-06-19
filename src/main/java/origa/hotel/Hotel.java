package origa.hotel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;
import java.util.HashSet;

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

    public Hotel(String name, String location, String address, String phoneNumber, int starCount, int roomCount, Set<Employee> employees) {
        if(starCount < 0 || starCount > 5) throw new IllegalArgumentException("Star count must be between 0 and 5");
        this.name = name;
        this.location = location;
        this.address = address;
        this.starCount = starCount;
        this.roomCount = roomCount;
        this.phoneNumber = phoneNumber;
        this.employees = new HashSet<>();
    }

    public void book(Guest guest, Room room){

    }

    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    public void removeEmployee(String id){
        employees.removeIf(e -> e.getId().equals(id));
    }

    public int countEmployees(Set<Employee> employees){
        int count = 0;
        for(Employee employee : employees){
            count++;
        }
        return count;
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
