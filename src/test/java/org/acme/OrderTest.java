package org.acme;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("customerLastnameInputValueAndExpectedViolationPairsProvider")
    void validateCustomerLastnameOnOrder(String customerLastnameInput, String expectedViolationMessage) {
        var order = new Order();
        order.setCustomerLastname(customerLastnameInput);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        if (expectedViolationMessage == null) {
            assertEquals(0, violations.size()); // valid = happy path
        } else {
            long count = violations.stream()
                    .filter(v -> v.getPropertyPath().toString().equals("customerLastname"))
                    .filter(v -> v.getMessage().equals(expectedViolationMessage))
                    .count();
            assertEquals(1, count); // invalid = violation path
        }
    }

    static Stream<Arguments> customerLastnameInputValueAndExpectedViolationPairsProvider() {
        return Stream.of(
                // input value, expected violation message
                Arguments.of(null, "must not be null"),
                Arguments.of("", "must not be empty"),
                Arguments.of("     ", "must not be blank"),
                Arguments.of("A", "size must be between 2 and 40"),
                Arguments.of("12345678901234567890123456789012345678901", "size must be between 2 and 40"),
                Arguments.of("Maier", null) // valid
        );
    }
}