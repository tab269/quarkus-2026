package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/orders")
public class OrdersResource {

    @GET
    public Response listOrders() {
        return Response.ok().build();
    }
}
