package com.yurii.pavlenko.coffeeshop.exceptions;

public class OrdersNotFoundException extends RuntimeException {
    public OrdersNotFoundException() {
        super("Not a single order was found!");
    }
}
