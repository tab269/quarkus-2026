package org.acme;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class OrdersResource {

    private final OrderService orderService;

    @Inject
    public OrdersResource(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostConstruct
    void init() {
        System.out.println("----- OrdersResource created -----");
    }

    @PreDestroy
    void cleanup() {
        System.out.println("----- OrdersResource shutting down -----");
    }

    @GET
    public Response listOrders() {
        return Response.ok(orderService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findOrderById(@PathParam("id") UUID id) {
        Optional<Order> order = orderService.findById(id);
        return order
                .map(o -> Response.ok(o).build())
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
        orderService.persist(order);
        URI location = UriBuilder
                .fromResource(OrdersResource.class)
                .path(order.getOrderId().toString())
                .build();
        return Response.created(location).build();
    }
}
