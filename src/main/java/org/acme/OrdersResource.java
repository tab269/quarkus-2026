package org.acme;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    private final Map<UUID, Order> orders = new HashMap<>();

    @GET
    public Response listOrders() {
        return Response.ok(orders.values()).build();
    }

    @GET
    @Path("/{id}")
    public Response findOrderById(@PathParam("id") UUID id) {
        return Optional.ofNullable(orders.get(id))
                .map(order -> Response.ok(order).build())
                .orElseGet(() -> Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("Order with id '" + id + "' not found")
                        .build());
    }

    @POST
    @APIResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Order.class)))
    public Response createOrder(@Valid Order order) {
        order.setOrderId(UUID.randomUUID());
        orders.put(order.getOrderId(), order);
        URI location = UriBuilder
                .fromResource(OrdersResource.class)
                .path(order.getOrderId().toString())
                .build();
        return Response.created(location).build();
    }
}
