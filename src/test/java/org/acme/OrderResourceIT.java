package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
class OrderResourceIT {

    @Test
    void testGetOnOrdersEndpoint_shouldReturnTestdata() {
        given()
          .when().get("/orders")
          .then()
             .statusCode(200)
             .body("customerFirstname", hasItem("Carola"))
            .body("customerFirstname", hasItem("Mike"))
            .body("customerFirstname", hasItem("Dörte"))
            .body("customerFirstname", hasItem("Anton-Peter"))
            .body("customerFirstname", hasItem("Anton Peter"))
            .body("customerFirstname", hasItem("Alex"))
            .body("customerFirstname", hasItem("Robbi"));
    }

    @Disabled // use unit test
    @Test
    void testPostWithOrderWithBlankFirstname_shouldResetFirstnameToNull() {
        Response postResponse = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(createTestOrderWithBlankFirstname())
                .when().post("/orders");
        postResponse.then()
                .statusCode(201)
                .header("Location", notNullValue());

        String location = postResponse.then().extract().header("Location");
        given()
                .when().get(location)
                .then()
                .statusCode(200)
                .body("customerFirstname", nullValue());
    }

    @Test
    void testPostThenGet() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(createOneTestOrder())
        .when().post("/orders")
                .then()
                .statusCode(201)
                .header("Location", notNullValue());

        given()
                .when().get("/orders")
                .then()
                .statusCode(200)
                .body("itemDescription", hasItem("Fussball"));
    }

    private OrderDTO createTestOrderWithBlankFirstname() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.orderId = UUID.randomUUID();
        orderDTO.customerFirstname = "  ";
        orderDTO.customerLastname = "Müller";
        orderDTO.itemDescription = "Fussball";
        orderDTO.amount = 72;
        return orderDTO;
    }

    private OrderDTO createOneTestOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.orderId = UUID.randomUUID();
        orderDTO.customerFirstname = "Thomas";
        orderDTO.customerLastname = "Müller";
        orderDTO.itemDescription = "Fussball";
        orderDTO.amount = 72;
        return orderDTO;
    }
}