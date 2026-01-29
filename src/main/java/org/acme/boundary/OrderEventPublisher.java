package org.acme.boundary;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class OrderEventPublisher {

    @Channel("orders")
    Emitter<OrderCreatedEvent> emitter;

    public void publish(OrderCreatedEvent event) {
        emitter.send(event);
    }
}
