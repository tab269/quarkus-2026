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

    private Order oneTestOrder;

    @BeforeEach
    void setUp() {
        // arrange
        oneTestOrder = createOneTestOrder();
    }

    @Test
    void findOrderById_happyPath() {
        ValidatableResponse validatableResponse = doOnePOSTrequest();

        // act
        given()
            .when()
                .get(validatableResponse.extract().response().header("Location"))
            // assert
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("customerLastname", equalTo(oneTestOrder.getCustomerLastname()))
                .body("customerFirstname", equalTo(oneTestOrder.getCustomerFirstname()))
                .body("itemDescription", equalTo(oneTestOrder.getItemDescription()))
                .body("amount", equalTo(oneTestOrder.getAmount()));
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
    void createOrder_shouldPersistOrder() {
        // act
        doOnePOSTrequest();

        // assert
        given()
            .when()
                .get("/orders")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("customerLastname", hasItem(oneTestOrder.getCustomerLastname()))
                .body("customerFirstname", hasItem(oneTestOrder.getCustomerFirstname()))
                .body("itemDescription", hasItem(oneTestOrder.getItemDescription()))
                .body("amount", hasItem(oneTestOrder.getAmount()));
    }

    private ValidatableResponse doOnePOSTrequest() {
        return given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(oneTestOrder)
            .when()
                .post("/orders")
            .then()
                .statusCode(201)
                .header("Location", notNullValue());
    }

    private static Order createOneTestOrder() {
        var order = new Order();
        order.setCustomerLastname("Doe");
        order.setCustomerFirstname("John");
        order.setItemDescription("Banana");
        order.setAmount(3);
        return order;
    }
}
