package org.acme.boundary;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;


@ApplicationScoped
public class OrderEventPublisher {

//    @Inject
//    @Channel("orders")
//    Emitter<OrderCreatedEvent> emitter;
//
//    public void publish(OrderCreatedEvent orderCreatedEvent) {
//        emitter.send(orderCreatedEvent);
//    }
}
