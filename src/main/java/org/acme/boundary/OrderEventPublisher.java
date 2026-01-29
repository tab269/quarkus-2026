package org.acme.boundary;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;


@ApplicationScoped
public class OrderEventPublisher {

    @Inject
    @Channel("orders-out")
    Emitter<OrderCreatedEvent> emitter;

    public void publish(OrderCreatedEvent orderCreatedEvent) {
        emitter.send(orderCreatedEvent);
    }
}
