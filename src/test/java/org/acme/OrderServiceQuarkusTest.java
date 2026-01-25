package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class OrderServiceQuarkusTest {

    @Inject
    OrderService cut; // class under test

    @Test
    void findAll_onEmptyDatabase_shouldReturnEmptyList() {
        // act
        List<OrderDTO> actualOrders = cut.findAll();

        // assert
        assertTrue(actualOrders.isEmpty());
    }

    @Test
    void persistOrder_shouldPersistValidOrder() {
        // arrange
        var orderDTO = new OrderDTO();
        orderDTO.setCustomerLastname("Doe");
        orderDTO.setCustomerFirstname("John");
        orderDTO.setItemDescription("Banana");
        orderDTO.setAmount(3);

        // act
        cut.persist(orderDTO);

        //assert
        assertNotNull(orderDTO.getId());
        assertNotNull(orderDTO.getOrderId());
    }
}