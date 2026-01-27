package org.acme;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

class OrderResourceTest {

    private OrderResource cut = new OrderResource(); // class under test

    @Test
    void erzeugeOrder_addOrderToMap() {
        // arrange
        UUID orderIdExpected = UUID.fromString("92f679e6-c082-442c-ab66-6d938b1a66c1");
        try (MockedStatic<UUID> mocked = mockStatic(UUID.class)) {
            mocked.when(UUID::randomUUID).thenReturn(orderIdExpected);
            OrderDTO testOrder = createTestOrderWithBlankFirstname();

            // act
            try (Response responseActual = cut.erzeugeOrder(testOrder)) {

                // assert
                assertEquals(201, responseActual.getStatus());
                URI location = responseActual.getLocation();
                URI expectedLocation = URI.create("/orders/" + orderIdExpected);
                assertEquals(expectedLocation, location);
                System.out.println("expectedLocation = " + expectedLocation);

                assertTrue(OrderResource.orders.containsKey(orderIdExpected));
            }
        }
    }

    private OrderDTO createTestOrderWithBlankFirstname() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.customerFirstname = "  ";
        orderDTO.customerLastname = "Müller";
        orderDTO.itemDescription = "Fussball";
        orderDTO.amount = 72;
        return orderDTO;
    }
}