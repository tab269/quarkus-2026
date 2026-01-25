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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    public static final String FOURTY_ONE_CHARS = "12345678901234567890123456789012345678901";
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
    @MethodSource("createOrdersTestSpecification")
    <T> void validateCustomerLastnameOnOrder(String fieldName, T fieldValue, String expectedViolationMessage)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Order testee = createTestOrder(fieldName, fieldValue);

        Set<ConstraintViolation<Order>> violations = validator.validate(testee);

        if (expectedViolationMessage == null) {
            assertEquals(0, violations.size()); // valid = happy path
        } else {
            long count = violations.stream()
                    .filter(v -> v.getPropertyPath().toString().equals(fieldName))
                    .filter(v -> v.getMessage().equals(expectedViolationMessage))
                    .count();
            assertEquals(1, count); // invalid = violation path
        }
    }

    private <T> Order createTestOrder(String fieldName, T fieldValue)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        var order = new Order();
        // initialize a valid object
        order.setCustomerLastname("valid from Test");
        order.setCustomerFirstname("valid from Test");
        order.setItemDescription("valid from Test");
        order.setAmount(10);

        // and override the field for the test at hand
        var propertyDescriptor = new PropertyDescriptor(fieldName, Order.class);
        Method setter = propertyDescriptor.getWriteMethod();
        setter.invoke(order, fieldValue);

        return order;
    }

    static Stream<Arguments> createOrdersTestSpecification() {
        return Stream.of(
                // fieldName, fieldValue, expectedViolationMessage
                Arguments.of("customerLastname", null, "must not be null"),
                Arguments.of("customerLastname", "", "must not be blank"),
                Arguments.of("customerLastname", "     ", "must not be blank"),
                Arguments.of("customerLastname", "A", "size must be between 2 and 40"),
                Arguments.of("customerLastname", FOURTY_ONE_CHARS, "size must be between 2 and 40"),
                Arguments.of("customerLastname", "Maier", null), // valid

                Arguments.of("customerFirstname", null, null), // valid
                Arguments.of("customerFirstname", "", null), // valid
                Arguments.of("customerFirstname", "     ", null), // valid
                Arguments.of("customerFirstname", "A", "size must be between 2 and 40"),
                Arguments.of("customerFirstname", FOURTY_ONE_CHARS, "size must be between 2 and 40"),
                Arguments.of("customerFirstname", "Max", null), // valid

                Arguments.of("itemDescription", null, "must not be null"),
                Arguments.of("itemDescription", "", "must not be blank"),
                Arguments.of("itemDescription", "     ", "must not be blank"),
                Arguments.of("itemDescription", "A", "size must be between 2 and 40"),
                Arguments.of("itemDescription", FOURTY_ONE_CHARS, "size must be between 2 and 40"),
                Arguments.of("itemDescription", "Banana", null), // valid

                Arguments.of("amount", 0, "must be greater than or equal to 1"),
                Arguments.of("amount", 101, "must be less than or equal to 100"),
                Arguments.of("amount", 42, null) // valid
        );
    }
}