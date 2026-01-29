package org.acme.boundary;

public class OrderCreatedEvent {
    private OrderDTO order;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(OrderDTO order) {
        this.order = order;
    }

    public OrderDTO getOrderId() {
        return order;
    }
}
