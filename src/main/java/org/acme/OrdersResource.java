package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/orders")
public class OrdersResource {

    private final Map<UUID, Order> orders = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listOrders() {
        return Response.ok(orders.values()).build();
    }

    @POST
    @APIResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Order.class)))
    public Response createOrder(Order order) {
        order.setOrderId(UUID.randomUUID());
        orders.put(order.getOrderId(), order);
        URI location = UriBuilder
                .fromResource(OrdersResource.class)
                .path("/orders/" + order.getOrderId())
                .build();
        return Response.created(location).build();
    }
}
