package com.yurii.pavlenko.coffeeshop.repository;

import com.yurii.pavlenko.coffeeshop.models.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public long nextId() {
        return idGenerator.getAndIncrement();
    }

    public void save(Order order) {
        orders.put(order.id(), order);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    public Order findById(long id) {
        return orders.get(id);
    }

    public void deleteAll() {
        orders.clear();
    }

    public void deleteById(long id) {
        orders.remove(id);
    }
}
