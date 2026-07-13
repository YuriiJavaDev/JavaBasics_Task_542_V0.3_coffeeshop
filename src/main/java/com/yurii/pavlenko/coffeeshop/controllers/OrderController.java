package com.yurii.pavlenko.coffeeshop.controllers;

import com.yurii.pavlenko.coffeeshop.config.CoffeeShopProperties;
import com.yurii.pavlenko.coffeeshop.exceptions.OrderNotFoundException;
import com.yurii.pavlenko.coffeeshop.models.*;
import com.yurii.pavlenko.coffeeshop.repository.OrderRepository;
import com.yurii.pavlenko.coffeeshop.validators.OrderValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    private final CoffeeShopProperties coffeeShopProperties;
    private final OrderValidator orderValidator;
    private final OrderRepository orderRepository;

    public OrderController(CoffeeShopProperties coffeeShopProperties, OrderValidator orderValidator, OrderRepository orderRepository) {
        this.coffeeShopProperties = coffeeShopProperties;
        this.orderValidator = orderValidator;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/shop-name")
    public String shopName() {
        return coffeeShopProperties.name();
    }

    @GetMapping("/max-orders")
    public int maxOrders() {
        return coffeeShopProperties.maxOrders();
    }

    @GetMapping("/info")
    public String info() {
        return "Coffee shop " + coffeeShopProperties.name() + " - " + coffeeShopProperties.city() + " - max orders: " + coffeeShopProperties.maxOrders();
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequestDTO request) {

        orderValidator.validate(request);

        Order order = new Order(orderRepository.nextId(), request.item(), request.quantity());

                orderRepository.save(order);

        OrderResponseDTO response = new OrderResponseDTO(
                order.id(),
                order.item(),
                order.quantity(),
                coffeeShopProperties.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/orders")
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> responses = new ArrayList<>();
        for (Order order : orders) {
            responses.add(new OrderResponseDTO(order.id(), order.item(), order.quantity(), coffeeShopProperties.name()));
        }
        return responses;
    }

    @GetMapping("/order/{id}")
    public OrderResponseDTO getOrder(@PathVariable long id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }
        return new OrderResponseDTO(order.id(), order.item(), order.quantity(), coffeeShopProperties.name());
    }
}