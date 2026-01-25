package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrderService {

    private final OrderRepository orderRepository;

    @Inject
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDTO> findAll() {
        return orderRepository.listAll()
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    public Optional<OrderDTO> findById(Long id) {
        OrderEntity entity = orderRepository.findById(id);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(OrderMapper.toDTO(entity));
    }

    public Optional<OrderDTO> findByOrderId(UUID orderId) {
        return orderRepository.findByOrderId(orderId)
                .map(OrderMapper::toDTO);
    }

    @Transactional
    public void persist(@Valid OrderDTO orderDTO) {
        orderDTO.setOrderId(UUID.randomUUID());
        // hier könnte noch weitere Business-Logik stehen
        OrderEntity entity = OrderMapper.toEntity(orderDTO);
        orderRepository.persist(entity);
        orderDTO.setId(entity.id);
    }
}
