package org.acme.domain.service;

import io.quarkus.narayana.jta.QuarkusTransaction;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.OrderEntity;
import org.acme.persistence.OrdersRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

@ApplicationScoped
public class OrderService {

    private static final ScheduledExecutorService DEFAULT_WORKER_POOL = Infrastructure.getDefaultWorkerPool();
    private final OrdersRepository orderRepo;

    @Inject
    public OrderService(OrdersRepository orderRepository) {
        this.orderRepo = orderRepository;
    }

    // FIXME: Laut Doku ist @Transactional in reaktiven Kontexten nicht erlaubt
    // @Transactional
    public Uni<OrderEntity> save(OrderEntity orderEntity) {
        orderEntity.setOrderId(UUID.randomUUID());

        return Uni.createFrom()
                .item(() -> {
                    QuarkusTransaction.begin();
                    try {
                        orderRepo.persist(orderEntity);
                        QuarkusTransaction.commit();
                        return orderEntity;
                    } catch (RuntimeException e) {
                        QuarkusTransaction.rollback();
                        throw e;
                    }
                }).runSubscriptionOn(DEFAULT_WORKER_POOL);
    }

    public Collection<OrderEntity> findAll() {
        return orderRepo.listAll();
    }


    public Uni<List<OrderEntity>> findAllReactive() {
        return Uni.createFrom()
                .item(orderRepo::listAll)
                .runSubscriptionOn(DEFAULT_WORKER_POOL);
    }

    public Optional<OrderEntity> findById(UUID orderId) {
        return orderRepo.findByOrderId(orderId);
    }
}
