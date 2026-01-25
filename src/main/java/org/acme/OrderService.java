package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.*;

@ApplicationScoped
public class OrderService {

    private final Map<UUID, OrderDTO> orders;

    public OrderService(Map<UUID, OrderDTO> orders) {
        this.orders = orders;
    }

    public Collection<OrderDTO> findAll() {
        return orders.values();
    }

    public Optional<OrderDTO> findById(UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    public void persist(@Valid OrderDTO order) {
        order.setOrderId(UUID.randomUUID());
        // hier könnte noch weitere Business-Logik stehen
        orders.put(order.getOrderId(), order);
    }
}
