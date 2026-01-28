package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.OrderEntity;

import java.util.*;

@ApplicationScoped
public class OrderService {

    private final Map<UUID, OrderEntity> orderRepository;

    @Inject
    public OrderService(Map<UUID, OrderEntity> orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(OrderEntity orderEntity) {
        orderRepository.put(orderEntity.getOrderId(), orderEntity);
    }

    public Collection<OrderEntity> findAll() {
        return orderRepository.values();
    }

    public Optional<OrderEntity> findById(UUID orderId) {
        return orderRepository.values().stream().filter(order -> order.getOrderId().equals(orderId)).findFirst();
    }
}
