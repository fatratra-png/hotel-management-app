package org.hotel;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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

  public abstract String work();

  public void addBonus(double bonus) {
    this.salary += bonus;
  }

  public double countSalaryByHour(double hourlyRate, int hoursWorked) {
    this.salary = hourlyRate * hoursWorked;
    return this.salary;
  }

  public abstract int countWorkHour();

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
