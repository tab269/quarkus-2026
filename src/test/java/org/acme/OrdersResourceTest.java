package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class OrdersResourceTest {

    private OrderDTO oneValidTestOrder;
    private OrderDTO oneInvalidTestOrder;

    @BeforeEach
    void setUp() {
        // arrange
        oneValidTestOrder = createOneValidTestOrder();
        oneInvalidTestOrder = createOneInvalidTestOrder();
    }

    @Test
    void findOrderById_happyPath() {
        ValidatableResponse validatableResponse = doOneValidPOSTrequest();

        // act
        given()
            .when()
                .get(validatableResponse.extract().response().header("Location"))
            // assert
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("customerLastname", equalTo(oneValidTestOrder.getCustomerLastname()))
                .body("customerFirstname", equalTo(oneValidTestOrder.getCustomerFirstname()))
                .body("itemDescription", equalTo(oneValidTestOrder.getItemDescription()))
                .body("amount", equalTo(oneValidTestOrder.getAmount()));
    }

    @Test
    void findOrderById_idNotFound() {
        // act
        given()
            .when()
                .get("/orders/db249802-145e-4921-8dfd-6dd50567c02f")
            .then()
                .statusCode(404)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void createOrder_shouldPersistValidOrder() {
        // act
        doOneValidPOSTrequest();

        // assert
        given()
            .when()
                .get("/orders")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("customerLastname", hasItem(oneValidTestOrder.getCustomerLastname()))
                .body("customerFirstname", hasItem(oneValidTestOrder.getCustomerFirstname()))
                .body("itemDescription", hasItem(oneValidTestOrder.getItemDescription()))
                .body("amount", hasItem(oneValidTestOrder.getAmount()));
    }

    @Test
    void createOrder_shouldRejectInvalidOrder() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(oneInvalidTestOrder)
            .when()
                .post("/orders")
            .then()
                .statusCode(400);
    }

    private ValidatableResponse doOneValidPOSTrequest() {
        return given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(oneValidTestOrder)
            .when()
                .post("/orders")
            .then()
                .statusCode(201)
                .header("Location", notNullValue());
    }

    private static OrderDTO createOneValidTestOrder() {
        var order = new OrderDTO();
        order.setCustomerLastname("Doe");
        order.setCustomerFirstname("John");
        order.setItemDescription("Banana");
        order.setAmount(3);
        return order;
    }

    private static OrderDTO createOneInvalidTestOrder() {
        var order = new OrderDTO();
        order.setCustomerLastname("Doe");
        order.setAmount(3);
        return order;
    }
}
