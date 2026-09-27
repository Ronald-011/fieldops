package com.fieldops.application;

import com.fieldops.enums.EmployeeStatus;
import com.fieldops.enums.JobRole;
import com.fieldops.enums.WorkShift;
import com.fieldops.model.Employee;

public class Main {
    public static void main(String[] args) {
        Employee employee = new Employee(
                1L,
                "Ana Silva",
                "00123",
                JobRole.FIELD_TECHNICIAN,
                WorkShift.MORNING,
                EmployeeStatus.AVAILABLE
        );

        System.out.println("=== FIELDOPS ===");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Registration: " + employee.getRegistration());
        System.out.println("Role: " + employee.getRole());
        System.out.println("Work shift: " + employee.getWorkShift());
        System.out.println("Status: " + employee.getStatus());
    }
}
