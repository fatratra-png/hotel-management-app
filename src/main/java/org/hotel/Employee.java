package org.hotel;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Employee {
    private String id;
    private String name;
    private String surname;
    private LocalDateTime dateOfBirth;
    private Job job;
    private String placeOfBirth;
    private String phoneNumber;
    private Hotel hotel;
    private double salary;

    public Employee(String id, String name, String surname, LocalDateTime dateOfBirth, Job job, String placeOfBirth, String phoneNumber, Hotel hotel) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.job = job;
        this.placeOfBirth = placeOfBirth;
        this.phoneNumber = phoneNumber;
        this.hotel = hotel;
        this.salary = 0.0;
    }

    public abstract String work();

    public double countBaseSalary() {
        return switch (job) {
            case MANAGER -> 5000.0;
            case RECEPTIONIST -> 2500.0;
            case COOK -> 3000.0;
            case SERVER -> 2000.0;
            case GUARD -> 2200.0;
            case HOUSEKEEPER -> 1800.0;
        };
    }

    public void addBonus(double bonus) {
        this.salary += bonus;
    }

    public double countSalaryByHour(double hourlyRate, int hoursWorked) {
        this.salary = hourlyRate * hoursWorked;
        return this.salary;
    }

    public int countWorkHour() {
        return switch (job) {
            case MANAGER -> 10;
            case RECEPTIONIST -> 8;
            case COOK -> 9;
            case SERVER -> 8;
            case GUARD -> 12;
            case HOUSEKEEPER -> 7;
        };
    }

    public double calculateOvertimePay(double hourlyRate, int overtimeHours) {
        return hourlyRate * 1.5 * overtimeHours;
    }

    public double calculateNightDifferential(double hourlyRate, int nightHours) {
        return hourlyRate * 1.25 * nightHours;
    }

    public double calculateDeductions(double taxRate) {
        return this.salary * (taxRate / 100);
    }

    public double calculateNetSalary(double taxRate) {
        return this.salary - calculateDeductions(taxRate);
    }
}
