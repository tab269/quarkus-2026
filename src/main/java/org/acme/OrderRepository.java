package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<OrderEntity> {

    public Optional<OrderEntity> findByOrderId(UUID orderId) {
        return find("orderId", orderId).firstResultOptional();
    }
}
