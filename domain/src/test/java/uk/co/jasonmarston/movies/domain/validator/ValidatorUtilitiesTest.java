package uk.co.jasonmarston.movies.domain.validator;

import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorUtilitiesTest {

    @Test
    void preconditionsShouldRejectNull() {
        NullPointerException ex = assertThrows(
            NullPointerException.class,
            () -> Preconditions.requireNonNull(null, "value must not be null")
        );

        assertEquals("value must not be null", ex.getMessage());
    }

    @Test
    void invariantsShouldRejectNonNull() {
        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> Invariants.requireNull("already-set", "must be null")
        );

        assertEquals("must be null", ex.getMessage());
    }

    @Test
    void invariantValidationShouldExposeConstraintFailures() {
        DomainInvariantViolationException ex = assertThrows(
            DomainInvariantViolationException.class,
            () -> InvariantValidation.INSTANCE.validate(new InvalidPayload())
        );

        assertEquals(1, ex.getViolations().size());
    }

    private static final class InvalidPayload {
        @NotBlank
        private final String value = "";
    }
}

