package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class OrderServiceQuarkusTest {

    private static final UUID JOHN_DOES_ORDER_ID = UUID.randomUUID();

    @Inject
    OrderService cut; // class under test

    @Produces
    @Alternative
    @Priority(1)
    @ApplicationScoped
    public Map<UUID, OrderDTO> produceTestOrders() {
        Map<UUID, OrderDTO> testOrdersMap = new HashMap<>();
        OrderDTO order = new OrderDTO();
        order.setOrderId(JOHN_DOES_ORDER_ID);
        order.setCustomerLastname("Doe");
        order.setCustomerFirstname("John");
        order.setItemDescription("Apple");
        order.setAmount(42);
        testOrdersMap.put(JOHN_DOES_ORDER_ID, order);
        return testOrdersMap;
    }

    @Test
    void findAll_shouldReturnJohnDoesOrder() {
        // act
        Collection<OrderDTO> actualOrders = cut.findAll();

        // assert
        assertEquals(1, actualOrders.size());
        OrderDTO actualOrder = actualOrders.iterator().next();
        assertEquals(JOHN_DOES_ORDER_ID, actualOrder.getOrderId());
        assertEquals("John", actualOrder.getCustomerFirstname());
        assertEquals("Doe", actualOrder.getCustomerLastname());
        assertEquals("Apple", actualOrder.getItemDescription());
        assertEquals(42, actualOrder.getAmount());
    }
}