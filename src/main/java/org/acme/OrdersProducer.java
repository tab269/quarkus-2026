package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class OrdersProducer {

    @Produces
    @ApplicationScoped
    public Map<UUID, Order> produceOrdersMap() {
        return new HashMap<>();
    }
}
