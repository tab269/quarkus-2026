package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.*;

@ApplicationScoped
public class OrderService {

    private final Map<UUID, Order> orders;

    public OrderService(Map<UUID, Order> orders) {
        this.orders = orders;
    }

    public Collection<Order> findAll() {
        return orders.values();
    }

    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    public void persist(@Valid Order order) {
        order.setOrderId(UUID.randomUUID());
        // hier könnte noch weitere Business-Logik stehen
        orders.put(order.getOrderId(), order);
    }
}
