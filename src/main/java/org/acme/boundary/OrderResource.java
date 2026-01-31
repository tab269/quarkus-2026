package org.acme.boundary;

import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.acme.domain.model.OrderEntity;
import org.acme.domain.service.OrderService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.acme.boundary.OrderMapper.toDTO;

@Path("/orders")
@PermitAll
public class OrderResource implements OrderAPI {

    @Inject
    JsonWebToken jwt;

    private final OrderService orderService;
    private final OrderEventPublisher orderEventPublisher;

    @Inject
    public OrderResource(OrderService orderService, OrderEventPublisher orderEventPublisher) {
        this.orderService = orderService;
        this.orderEventPublisher = orderEventPublisher;
    }

    public Response erzeugeOrder(@Valid OrderDTO orderDTO) {
        if (orderDTO.customerFirstname == null || orderDTO.customerFirstname.isBlank()) {
            orderDTO.customerFirstname = null;
        }
        OrderEntity orderEntity = OrderMapper.toEntity(orderDTO);
        OrderEntity orderEntityPersisted = orderService.save(orderEntity);
        OrderDTO orderDTOResult = toDTO(orderEntityPersisted);
        URI location = UriBuilder
                .fromResource(OrderResource.class)
                .path(orderDTOResult.getOrderId().toString())
                .build();

        // TODO: auskommentiert, weil Artemis Server (ActiveMQ) nicht zum Laufen gebracht wurde
        // orderEventPublisher.publish(new OrderCreatedEvent(orderDTOResult));
        System.out.println("Order Created + " + orderDTOResult);

        return Response.created(location).build();
    }

    @GET
    @Path("/findByAmountGreaterThan")
    @RolesAllowed("User")
    public Response findOrdersByAmountGreaterThan(@QueryParam("amount") int amount) {
        List<OrderDTO> foundOrders = orderService.findAll().stream()
                .filter(orderEntity -> orderEntity.getAmount() > amount)
                .map(OrderMapper::toDTO)
                .toList();
        return Response.ok(foundOrders).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("User")
    @Path("/filter")
    public Response filter(OrderFilterDTO orderFilterDTO) {
        OrderDTO.builder().orderId(UUID.randomUUID()).amount(17).build();
        List<OrderDTO> foundOrders = orderService.findAll().stream()
                .filter(o -> switch (orderFilterDTO.getFilterOperator()) {
                                        case LESS_THAN -> o.getAmount() < orderFilterDTO.getAmount();
                                        case GREATER_THAN -> o.getAmount() > orderFilterDTO.getAmount();
                    })
                .map(OrderMapper::toDTO)
                .toList();
        return Response.ok(foundOrders).build();
    }

    @GET
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@Context SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        System.out.println("GET /orders called with: " + String.format("name = %s,"
                        + " isHttps: %s,"
                        + " authScheme: %s,"
                        + " hasJWT: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt()));

        return Response.ok(orderService.findAll().stream()
                .map(OrderMapper::toDTO)
                .toList()).build();
    }

    private boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }

    @GET
    @RolesAllowed("User")
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("orderId") UUID orderId) {
        return orderService.findById(orderId)
                .map(entity -> Response.ok(toDTO(entity)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Es existiert keine OrderDTO mit der orderId " + orderId).build());
    }
}
