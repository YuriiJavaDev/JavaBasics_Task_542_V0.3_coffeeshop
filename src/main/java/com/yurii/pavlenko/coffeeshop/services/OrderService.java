package com.yurii.pavlenko.coffeeshop.services;

import com.yurii.pavlenko.coffeeshop.exceptions.OrderNotFoundException;
import com.yurii.pavlenko.coffeeshop.exceptions.OrdersNotFoundException;
import com.yurii.pavlenko.coffeeshop.models.Order;
import com.yurii.pavlenko.coffeeshop.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderOrThrow(long id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }
        return order;
    }

    public void ensureOrdersExist() {
        if (orderRepository.findAll().isEmpty()) {
            throw new OrdersNotFoundException();
        }
    }
}
