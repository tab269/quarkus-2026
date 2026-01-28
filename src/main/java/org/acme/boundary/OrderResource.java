package org.acme.boundary;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.acme.domain.model.OrderEntity;
import org.acme.domain.service.OrderService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/orders")
public class OrderResource implements OrderAPI {

    OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    // public Response erzeugeOrder(@Valid OrderDTO orderDTO, @Context UriInfo uriInfo) {
    public Response erzeugeOrder(@Valid OrderDTO orderDTO) {
        // Alternative Möglichkeit zur Valid-Annotation am Parameter,
        // um z.B. Fehlermeldungen zu loggen aber NICHT via REST zu responden (security)
        // Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        // Set<ConstraintViolation<OrderDTO>> validationResult = validator.validate(orderDTO);
        // validationResult.forEach(violation -> {
        //     violation.getPropertyPath().forEach(propertyPath -> {
        //         ...
        //     });
        // });
        if (orderDTO.customerFirstname == null || orderDTO.customerFirstname.isBlank()) {
            orderDTO.customerFirstname = null;
        }
        OrderEntity orderEntity = OrderMapper.toEntity(orderDTO);
        OrderEntity orderEntityPersisted = orderService.save(orderEntity);
        OrderDTO orderDTOResult = OrderMapper.toDTO(orderEntityPersisted);
        URI location = UriBuilder
                .fromResource(OrderResource.class)
                .path(orderDTOResult.getOrderId().toString())
                .build();

        return Response.created(location).build();
    }

    @GET
    @Path("/findByAmountGreaterThan")
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
    @Path("/filter")
    public Response filter(OrderFilterDTO orderFilterDTO) {
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        return Response.ok(orderService.findAll().stream()
                .map(OrderMapper::toDTO)
                .toList()).build();
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("orderId") UUID orderId) {
        return orderService.findById(orderId)
                .map(entity -> Response.ok(OrderMapper.toDTO(entity)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Es existiert keine OrderDTO mit der orderId " + orderId).build());
    }
}
