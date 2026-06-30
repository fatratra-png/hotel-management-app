package org.hotel;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void testEmployeeCommonCalculations() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var m = new Manager("m100","John","Doe", LocalDateTime.now(), "PB", "000", hotel);
        m.addBonus(50);
        double salary = m.countSalaryByHour(20, 5);
        assertEquals(100, salary);
        assertEquals(8, m.countWorkHour());
        assertTrue(m.getJob() == Job.MANAGER);
        double overtime = m.calculateOvertimePay(20, 2);
        assertEquals(60, overtime);
        double night = m.calculateNightDifferential(20, 2);
        assertEquals(50, night);
        double ded = m.calculateDeductions(10);
        assertEquals(m.getSalary() * 0.10, ded);
        double net = m.calculateNetSalary(10);
        assertEquals(m.getSalary() - ded, net);
    }

    @Test
    public void testBonusAccumulatesOnCurrentSalary() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var manager = new Manager("m101","John","Doe", LocalDateTime.now(), "PB", "000", hotel);

        manager.addBonus(50);
        manager.addBonus(25);

        assertEquals(2075, manager.getSalary());
    }

    @Test
    public void testCountSalaryByHourReplacesPreviousSalary() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var manager = new Manager("m102","John","Doe", LocalDateTime.now(), "PB", "000", hotel);
        manager.addBonus(500);

        manager.countSalaryByHour(15, 10);

        assertEquals(150, manager.getSalary());
    }

    @Test
    public void testZeroTaxKeepsNetSalaryEqualToGrossSalary() {
        var hotel = new Hotel("H","L","A","P",4,5);
        var manager = new Manager("m103","John","Doe", LocalDateTime.now(), "PB", "000", hotel);
        manager.countSalaryByHour(30, 8);

        assertEquals(0, manager.calculateDeductions(0));
        assertEquals(240, manager.calculateNetSalary(0));
    }
}
