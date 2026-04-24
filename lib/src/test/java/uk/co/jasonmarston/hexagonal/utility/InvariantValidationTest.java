package uk.co.jasonmarston.hexagonal.utility;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvariantValidationTest {

    @Test
    void validateShouldPassWhenConstraintsAreSatisfied() {
        assertDoesNotThrow(() -> InvariantValidation.INSTANCE.validate(new ValidPayload("Arrival")));
    }

    @Test
    void validateShouldThrowConstraintViolationExceptionWhenConstraintsFail() {
        ConstraintViolationException exception = assertThrows(
            ConstraintViolationException.class,
            () -> InvariantValidation.INSTANCE.validate(new InvalidPayload(""))
        );

        assertEquals(1, exception.getConstraintViolations().size());
        assertEquals(
            "value",
            exception.getConstraintViolations().iterator().next().getPropertyPath().toString()
        );
    }

    private static final class ValidPayload {
        @NotBlank
        private final String value;

        private ValidPayload(String value) {
            this.value = value;
        }
    }

    private static final class InvalidPayload {
        @NotBlank
        private final String value;

        private InvalidPayload(String value) {
            this.value = value;
        }
    }
}

