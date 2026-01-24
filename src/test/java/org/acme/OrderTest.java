package org.acme;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    private static Validator validator;
    private static ValidatorFactory validatorFactory;

    @BeforeAll
    static void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        validatorFactory.close();
    }

    @Test
    void validateOrder_withNullCustomerLastname_shouldFail() {
        Set<ConstraintViolation<Order>> violations = validator.validate(new Order());
        assertEquals(1, violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("customerLastname"))
                .filter(v -> v.getMessage().equals("must not be null"))
                .count());
    }

    @Test
    void validateOrder_withEmptyCustomerLastname_shouldFail() {
        var order = new Order();
        order.setCustomerLastname("");
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(1, violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("customerLastname"))
                .filter(v -> v.getMessage().equals("must not be empty"))
                .count());
    }

    @Test
    void validateOrder_withBlankCustomerLastname_shouldFail() {
        var order = new Order();
        order.setCustomerLastname("   ");
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(1, violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("customerLastname"))
                .filter(v -> v.getMessage().equals("must not be blank"))
                .count());
    }

    @Test
    void validateOrder_withTooShortCustomerLastname_shouldFail() {
        var order = new Order();
        order.setCustomerLastname("A");
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(1, violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("customerLastname"))
                .filter(v -> v.getMessage().equals("size must be between 2 and 40"))
                .count());
    }

    @Test
    void validateOrder_withTooLongCustomerLastname_shouldFail() {
        var order = new Order();
        order.setCustomerLastname("12345678901234567890123456789012345678901");
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(1, violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("customerLastname"))
                .filter(v -> v.getMessage().equals("size must be between 2 and 40"))
                .count());
    }

    @Test
    void validateOrder_withValidCustomerLastname_shouldPass() {
        var order = new Order();
        order.setCustomerLastname("Maier");
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(0, violations.size());
    }
}