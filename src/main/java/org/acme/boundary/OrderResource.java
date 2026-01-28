package org.acme.boundary;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.acme.domain.model.OrderEntity;
import org.acme.domain.service.OrderService;

import java.net.URI;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static org.acme.boundary.OrderMapper.toDTO;

@Path("/orders")
public class OrderResource implements OrderAPI {

    OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    public Uni<Response> erzeugeOrder(@Valid OrderDTO orderDTO) {
        if (orderDTO.customerFirstname == null || orderDTO.customerFirstname.isBlank()) {
            orderDTO.customerFirstname = null;
        }
        OrderEntity orderEntity = OrderMapper.toEntity(orderDTO);
        /* bis hier läuft alles noch im Event-Loop-Thread: ist OK, weil kurz und CPU-only */

        return orderService.save(orderEntity)
                .onItem().transform(persistedEntity -> {
                   var orderDTOResult = toDTO(persistedEntity);

                    URI location = UriBuilder
                            .fromResource(OrderResource.class)
                            .path(orderDTOResult.getOrderId().toString())
                            .build();


                   return Response.created(location).build();
                });
    }

    @GET
    @Path("/findByAmountGreaterThan")
    public Uni<Response> findOrdersByAmountGreaterThan(@QueryParam("amount") int amount) {
        return orderService.findAll()
                .onItem().transform(orderEntities ->
                        orderEntities.stream()
                                .filter(orderEntity -> orderEntity.getAmount() > amount)
                                .map(OrderMapper::toDTO)
                                .toList())
                .onItem().transform(orderDtos -> Response.ok(orderDtos).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filter")
    public Uni<Response> filter(OrderFilterDTO orderFilterDTO) {

        return orderService.findAll()
                .onItem().transform(orders ->
                        orders.stream()
                                .filter(orderEntity -> switch (orderFilterDTO.getFilterOperator()) {
                                    case LESS_THAN -> orderEntity.getAmount() < orderFilterDTO.getAmount();
                                    case GREATER_THAN -> orderEntity.getAmount() > orderFilterDTO.getAmount();
                                }).map(OrderMapper::toDTO)
                                .toList())
                .onItem().transform(orderDtos ->
                        Response.ok(orderDtos).build());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getOrders() {
        return orderService.findAll()
                .onItem()
                .transform(orderEntities -> Response.ok(orderEntities).build());
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getOrder(@PathParam("orderId") UUID orderId) {
        return orderService.findById(orderId)
                .onItem().ifNotNull().transform(orderEntity -> Response.ok(toDTO(orderEntity)).build())
                .onItem().ifNull().continueWith(Response.status(NOT_FOUND).build());
    }
}
