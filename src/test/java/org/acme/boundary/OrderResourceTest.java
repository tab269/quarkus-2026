package org.acme.boundary;

import jakarta.ws.rs.core.Response;
import org.acme.domain.model.OrderEntity;
import org.acme.domain.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderResourceTest {

    private OrderResource cut; // class under test

    OrderService orderServiceMock;
    OrderEventPublisher publisherMock;

    @BeforeEach
    public void setUp() {
        orderServiceMock = Mockito.mock(OrderService.class);
        publisherMock = Mockito.mock(OrderEventPublisher.class);

        cut = new OrderResource(orderServiceMock, publisherMock);
    }

    @Test
    void erzeugeOrder_addOrderToMap() {
        // arrange
        UUID orderIdExpected = UUID.fromString("92f679e6-c082-442c-ab66-6d938b1a66c1");
        var dummyOrderEntity = new OrderEntity();
        dummyOrderEntity.setId(42L);
        dummyOrderEntity.setOrderId(orderIdExpected);
        dummyOrderEntity.setCustomerName("TestName");
        dummyOrderEntity.setItemDescription("TestItem");
        dummyOrderEntity.setAmount(99);
        when(orderServiceMock.save(any())).thenReturn(dummyOrderEntity);

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

                verify(orderServiceMock).save(any());
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