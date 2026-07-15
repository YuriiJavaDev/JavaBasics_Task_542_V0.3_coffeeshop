package com.yurii.pavlenko.coffeeshop.repository;

import com.yurii.pavlenko.coffeeshop.models.Order;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderRepository.class);

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public long nextId() {
        return idGenerator.getAndIncrement();
    }

    public void save(Order order) {
        orders.put(order.id(), order);
        log.info("The order has been saved: id={}, title={}", order.id(), order.item());
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    public Order findById(long id) {
        return orders.get(id);
    }

    public void deleteAll() {
        orders.clear();
        log.info("All orders have been deleted!");
    }

    public void deleteById(long id) {
        orders.remove(id);
        log.info("The order has been deleted: id={}", id);
    }
}
