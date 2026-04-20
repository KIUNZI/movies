package uk.co.jasonmarston.hexagonal.utility;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


import java.util.Set;

import static jakarta.validation.Validation.*;

public enum InvariantValidation {
    INSTANCE;

    private final Validator validator;

    private InvariantValidation() {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    public <T> void validate(final T valueObject) {
        final Set<ConstraintViolation<T>> violations = validator.validate(valueObject);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
