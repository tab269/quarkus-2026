package org.acme.boundary;

import jakarta.ws.rs.core.Response;
import org.acme.domain.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderResourceTest {

    private OrderResource cut; // class under test

    OrderService orderService;


    @BeforeEach
    public void setUp() {
        orderService = Mockito.mock(OrderService.class);
        cut = new OrderResource(orderService);
    }

    @Test
    void erzeugeOrder_addOrderToMap() {
        when(orderService.findById(any())).thenReturn(Optional.empty());

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

//                assertTrue(orderService.findById(orderIdExpected).isEmpty());
                verify(orderService).save(any());
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