package uk.co.jasonmarston.movies.domain.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;

import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

/**
 * Singleton enum that provides Bean Validation for domain value objects and aggregates.
 *
 * <p>Use {@link #INSTANCE} to obtain the single validator and call
 * {@link #validate(Object)} to check all Bean Validation constraints declared on a domain
 * object. Any constraint violations are collected and re-thrown as a single
 * {@link DomainInvariantViolationException}.</p>
 *
 * @see DomainInvariantViolationException
 */
public enum InvariantValidation {
    INSTANCE;

    private final Validator validator;

    InvariantValidation() {
        try (final ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    /**
     * Validates all Bean Validation constraints declared on {@code valueObject}.
     *
     * @param <T>         the type of the object to validate
     * @param valueObject the domain object to validate; must not be {@code null}
     * @throws DomainInvariantViolationException if one or more constraints are violated
     */
    public <T> void validate(final T valueObject) {
        final Set<ConstraintViolation<T>> violations = validator
            .validate(valueObject);

        if (!violations.isEmpty()) {
            throw new DomainInvariantViolationException(violations);
        }
    }
}
