package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/orders")
public class OrdersResource {

    private final Order dummyOrder;
    {
        dummyOrder = new Order();
        dummyOrder.setOrderId(UUID.randomUUID());
        dummyOrder.setCustomerLastname("Doe");
        dummyOrder.setCustomerFirstname("John");
        dummyOrder.setItemDescription("Apple");
        dummyOrder.setAmount(5);
    }

    @GET
    public Response listOrders() {
        return Response.ok(List.of(dummyOrder)).build();
    }
}
