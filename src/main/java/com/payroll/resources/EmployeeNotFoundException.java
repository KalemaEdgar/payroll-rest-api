package com.payroll.resources;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Could not find employee with id %d".formatted(id));
    }
}
