package org.acme.boundary;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

public interface OrderAPI {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Bestellung anlegen")
    @APIResponse(
            responseCode = "201",
            description = "Bestellung erstellt",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDTO.class),
                    examples = @ExampleObject(
                            name = "success",
                            summary = "Erfolgreiche Antwort",
                            value = """
                {
                  "orderId": "5cd863eb-18e2-407f-a548-936209e9ac37",
                  "customerLastname": "Mueller",
                  "customerFirst": "Thomas",
                  "itemDescription": "Banane",
                  "amount": 5
                }
                """
                    )
            )
    )
    Uni<Response> erzeugeOrder(@Valid
                          @RequestBody(
                                  content = @Content(
                                          mediaType = "application/json",
                                          schema = @Schema(implementation = OrderDTO.class),
                                          examples = @ExampleObject(
                                                  name = "orderToBeCreated",
                                                  summary = "Die Order, die angelegt werden soll",
                                                  value = """
                                                          {
                                                          "customerLastname": "Mueller",
                                                          "customerFirst": "Thomas",
                                                          "itemDescription": "Banane",
                                                          "amount": 5
                                                          }
                                                          """
                                          )
                                  )
                          )
                          OrderDTO orderDTO);
}
