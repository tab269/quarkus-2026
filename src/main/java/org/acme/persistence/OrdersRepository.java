package org.acme.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.model.OrderEntity;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrdersRepository implements PanacheRepository<OrderEntity> {

    public Optional<OrderEntity> findByOrderId(UUID orderId) {
        return find("orderId", orderId).firstResultOptional();
    }
}
