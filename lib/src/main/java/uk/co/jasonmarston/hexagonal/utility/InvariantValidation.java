package uk.co.jasonmarston.hexagonal.utility;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


import java.util.Set;

import static jakarta.validation.Validation.*;

/**
 * Singleton helper that validates objects using Bean Validation constraints.
 *
 * <p>Use {@link #INSTANCE} to access the validator and call {@link #validate(Object)}
 * to enforce all declared constraints on a candidate object.</p>
 *
 * @see ConstraintViolationException
 * @see Validator
 */
public enum InvariantValidation {
    /**
     * Global validation instance.
     */
    INSTANCE;

    private final Validator validator;

    InvariantValidation() {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    /**
     * Validates the provided object and throws when constraints are violated.
     *
     * @param <T> the type of object being validated
     * @param valueObject the object to validate
     * @throws ConstraintViolationException if one or more constraints are violated
     */
    public <T> void validate(final T valueObject) {
        final Set<ConstraintViolation<T>> violations = validator.validate(valueObject);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
