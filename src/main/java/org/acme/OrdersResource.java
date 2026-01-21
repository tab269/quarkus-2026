package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/orders")
public class OrdersResource {

    @GET
    public Response listOrders() {
        return Response.ok(List.of()).build();
    }
}
