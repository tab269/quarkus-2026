package org.acme.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
class OrderResourceIT {

//    @Inject
//    OrderService orderService;
//
//    @BeforeEach
//    void resetDb() {
//        orderService.clearAll();
//    }

    // FIXME: wir resetten aktuell vor jedem Test die Datenbank nicht
    @Disabled
    @Test
    void testGetOnOrdersEndpoint_shouldReturnTestdata() {
        given()
          .when().get("/orders")
          .then()
             .statusCode(200)
             .body("customerLastname", hasItem("Carola"))
//            .body("customerLastname", hasItem("Mike"))
//            .body("customerLastname", hasItem("Dörte"))
//            .body("customerLastname", hasItem("Anton-Peter"))
//            .body("customerFirstname", hasItem("Anton"))
//            .body("customerLastname", hasItem("Alex"))
//            .body("customerLastname", hasItem("Robbi"));
        ;
    }

    @Disabled // use unit test
    @Test
    void testPostWithOrderWithBlankFirstname_shouldResetFirstnameToNullOnPersistedOrder() {
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