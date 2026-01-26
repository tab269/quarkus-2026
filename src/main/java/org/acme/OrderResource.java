package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/orders")
public class OrderResource {

    private Map<UUID, Order> orders = new HashMap<>();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response erzeugeOrder(Order order) {
        order.orderId = UUID.randomUUID();
        orders.put(order.orderId, order);
        return Response.ok(order).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        return Response.ok(orders.values()).build();
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("orderId") UUID orderId) {
        if (orders.containsKey(orderId)) {
            return Response.ok(orders.get(orderId)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Es existiert keine Order mit der orderId " + orderId).build();
    }
}
