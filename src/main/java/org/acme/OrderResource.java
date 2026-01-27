package org.acme;

import io.quarkus.runtime.util.StringUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.*;

@Path("/orders")
public class OrderResource implements OrderAPI {

    private final Map<UUID, OrderDTO> orders = new HashMap<>();
    {
        orders.put(UUID.randomUUID(), new OrderDTO() {{ customerFirstname = "Alex"; amount = 1;}});
        orders.put(UUID.randomUUID(), new OrderDTO() {{ customerFirstname = "Mike"; amount = 5;}});
        orders.put(UUID.randomUUID(), new OrderDTO() {{ customerFirstname = "Dörte"; amount = 5;}});
        orders.put(UUID.randomUUID(), new OrderDTO() {{ customerFirstname = "Robbi"; amount = 72;}});
        orders.put(UUID.randomUUID(), new OrderDTO() {{ customerFirstname = "Anton-Peter"; amount = 72;}});
        orders.put(UUID.randomUUID(), new OrderDTO() {{ customerFirstname = "Anton Peter"; amount = 72;}});
        orders.put(UUID.randomUUID(), new OrderDTO() {{ customerFirstname = "Carola"; amount = 60;}});
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response erzeugeOrder(@Valid OrderDTO orderDTO, @Context UriInfo uriInfo) {
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<OrderDTO>> validationResult = validator.validate(orderDTO);
//        validationResult.forEach(violation -> {
//           violation.getPropertyPath().forEach(propertyPath -> {
//
//           });
//        });
        if (StringUtil.isNullOrEmpty(orderDTO.customerFirstname)) {
            orderDTO.customerFirstname = null;
        }
        orderDTO.orderId = UUID.randomUUID();
        orders.put(orderDTO.orderId, orderDTO);
        URI location = uriInfo.getAbsolutePathBuilder().path(orderDTO.orderId.toString()).build();

//        return Response.status(Response.Status.CREATED).location(location).build();
        return Response.created(location).build();
    }

    @GET
    @Path("/findByAmountGreaterThan")
    public Response findOrdersByAmountGreaterThan(@QueryParam("amount") int amount) {
        List<OrderDTO> foundOrders = orders.values().stream().filter(orderDTO -> orderDTO.amount > amount).toList();
        return Response.ok(foundOrders).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filter")
    public Response filter(OrderFilterDTO orderFilterDTO) {
        List<OrderDTO> foundOrders = orders.values().stream()
                .filter(o -> switch (orderFilterDTO.getFilterOperator()) {
                                        case LESS_THAN -> o.amount < orderFilterDTO.getAmount();
                                        case GREATER_THAN -> o.amount > orderFilterDTO.getAmount();
                    }).toList();
        return Response.ok(foundOrders).build();
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
                .entity("Es existiert keine OrderDTO mit der orderId " + orderId).build();
    }
}
