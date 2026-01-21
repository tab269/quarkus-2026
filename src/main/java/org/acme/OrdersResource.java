package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/orders")
public class OrdersResource {

    private final Map<UUID, Order> orders =  new HashMap<>();

    {
        Order dummyOrder = new Order();
        dummyOrder.setOrderId(UUID.randomUUID());
        dummyOrder.setCustomerLastname("Doe");
        dummyOrder.setCustomerFirstname("John");
        dummyOrder.setItemDescription("Apple");
        dummyOrder.setAmount(5);
        orders.put(dummyOrder.getOrderId(), dummyOrder);
        dummyOrder = new Order();
        dummyOrder.setOrderId(UUID.randomUUID());
        dummyOrder.setCustomerLastname("Russel");
        dummyOrder.setCustomerFirstname("Jack");
        dummyOrder.setItemDescription("Banana");
        dummyOrder.setAmount(3);
        orders.put(dummyOrder.getOrderId(), dummyOrder);
    }

    @GET
    public Response listOrders() {
        return Response.ok(orders.values()).build();
    }
}
