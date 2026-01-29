package org.acme.boundary;

public class OrderCreatedEvent {
    private OrderDTO order;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(OrderDTO order) {
        this.order = order;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
