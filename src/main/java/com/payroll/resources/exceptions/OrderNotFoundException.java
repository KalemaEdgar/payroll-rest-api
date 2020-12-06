package com.payroll.resources.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Could not find order with id %d".formatted(id));
    }
}