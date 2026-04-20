package uk.co.jasonmarston.movies.domain.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;

import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

public enum InvariantValidation {
    INSTANCE;

    private final Validator validator;

    private InvariantValidation() {
        try (final ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    public <T> void validate(final T valueObject) {
        final Set<ConstraintViolation<T>> violations = validator
            .validate(valueObject);

        if (!violations.isEmpty()) {
            throw new DomainInvariantViolationException(violations);
        }
    }
}
