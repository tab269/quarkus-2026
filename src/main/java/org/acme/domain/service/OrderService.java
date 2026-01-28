package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.model.OrderEntity;
import org.acme.persistence.OrdersRepository;

import java.util.*;

@ApplicationScoped
public class OrderService {

    private final OrdersRepository orderOrdersRepository;

    @Inject
    public OrderService(OrdersRepository orderRepository) {
        this.orderOrdersRepository = orderRepository;
    }

    @Transactional
    public OrderEntity save(OrderEntity orderEntity) {
        orderEntity.setOrderId(UUID.randomUUID());
        orderOrdersRepository.persist(orderEntity);
        return orderEntity;
    }

    public Collection<OrderEntity> findAll() {
        return orderOrdersRepository.listAll();
    }

    public Optional<OrderEntity> findById(UUID orderId) {
        return orderOrdersRepository.findByOrderId(orderId);
    }
}
