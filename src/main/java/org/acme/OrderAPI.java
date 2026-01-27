package org.acme;

import jakarta.validation.Valid;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

public interface OrderAPI {

    @APIResponse()
    Response erzeugeOrder(@Valid OrderDTO orderDTO, @Context UriInfo uriInfo);
}
