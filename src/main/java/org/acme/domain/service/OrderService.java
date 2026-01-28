package org.acme.domain.service;

import io.quarkus.narayana.jta.QuarkusTransaction;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.OrderEntity;
import org.acme.persistence.OrdersRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

@ApplicationScoped
public class OrderService {

    public static final ScheduledExecutorService DEFAULT_WORKER_POOL = Infrastructure.getDefaultWorkerPool();
    private final OrdersRepository ordersRepo;

    @Inject
    public OrderService(OrdersRepository orderRepository) {
        this.ordersRepo = orderRepository;
    }

    // hier noch klassische Transaktion weil Panache blockierend ist
    // bei reactive-Panache dann @WithTransaction verwenden!
    // ABER: @Transactional muss hier weg und **in das Uni rein**!
    // @Transactional
    public Uni<OrderEntity> save(OrderEntity orderEntity) {
        orderEntity.setOrderId(UUID.randomUUID());

        return Uni.createFrom()
                .item(() -> {
                    QuarkusTransaction.begin();
                    try {
                        ordersRepo.persist(orderEntity);
                        QuarkusTransaction.commit();
                        return orderEntity;
                    } catch (RuntimeException e) {
                        QuarkusTransaction.rollback();
                        throw e;
                    }
                }).runSubscriptionOn(DEFAULT_WORKER_POOL);
    }

    public Uni<List<OrderEntity>> findAll() {
        return Uni.createFrom()
                .item(ordersRepo::listAll)
                .runSubscriptionOn(DEFAULT_WORKER_POOL);
    }

    public Uni<OrderEntity> findById(UUID orderId) {
        return Uni.createFrom()
                .item(() -> ordersRepo.findByOrderId(orderId).orElse(null))
                .runSubscriptionOn(DEFAULT_WORKER_POOL);
    }
}
