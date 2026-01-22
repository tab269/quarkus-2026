package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class OrdersResourceTest {

    @Test
    void createOrder_shouldPersistOrder() {
        // arrange
        var order = new Order();
        order.setCustomerLastname("Doe");
        order.setCustomerFirstname("John");
        order.setItemDescription("Banana");
        order.setAmount(3);

        // act
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(order)
            .when()
                .post("/orders")
            .then()
                .statusCode(201)
                .header("Location", notNullValue());

        // assert
        given()
            .when()
                .get("/orders")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("customerLastname", hasItem(order.getCustomerLastname()))
                .body("customerFirstname", hasItem(order.getCustomerFirstname()))
                .body("itemDescription", hasItem(order.getItemDescription()))
                .body("amount", hasItem(order.getAmount()));
    }
}
