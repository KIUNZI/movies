package uk.co.jasonmarston.movies.domain.exception;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomainInvariantViolationExceptionTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldCapturePropertyAndMessageFromConstraintViolations() {
        var violations = validator.validate(new InvalidObject());

        DomainInvariantViolationException exception = new DomainInvariantViolationException(violations);

        assertEquals(1, exception.getViolations().size());
        DomainInvariantViolationException.Violation violation = exception.getViolations().iterator().next();
        assertEquals("name", violation.property());
        assertEquals("must not be blank", violation.message());
        assertTrue(exception.getMessage().contains("name must not be blank"));
    }

    private static final class InvalidObject {
        @NotBlank
        private final String name = "";
    }
}

