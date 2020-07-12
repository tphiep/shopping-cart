package com.example.exceptions;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException() {
    }
}
