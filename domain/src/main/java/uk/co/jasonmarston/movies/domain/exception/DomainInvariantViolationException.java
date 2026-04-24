package uk.co.jasonmarston.movies.domain.exception;

import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Exception thrown when one or more Bean Validation constraints are violated on a domain
 * object during invariant checking.
 *
 * <p>Each violated constraint is captured as a {@link Violation} record and made
 * available via {@link #getViolations()}. The exception message is a comma-separated
 * summary of all violations in the form {@code "property message"}.</p>
 *
 * @see uk.co.jasonmarston.movies.domain.validator.InvariantValidation
 */
@Getter
public class DomainInvariantViolationException
        extends RuntimeException {

    private final Set<Violation> violations;

    /**
     * Constructs a {@code DomainInvariantViolationException} from the given set of
     * constraint violations.
     *
     * @param violations the non-empty set of constraint violations; must not be {@code null}
     */
    public DomainInvariantViolationException(
            final Set<? extends jakarta
                    .validation
                    .ConstraintViolation<?>> violations
    ) {
        super(buildMessage(violations));
        this.violations = violations.stream()
            .map(Violation::from)
            .collect(Collectors.toUnmodifiableSet());
    }

    private static String buildMessage(
            final Set<? extends jakarta.validation.ConstraintViolation<?>> violations
    ) {
        return violations.stream()
            .map(v -> v.getPropertyPath() + " " + v.getMessage())
            .collect(Collectors.joining(", "));
    }

    /**
     * An immutable summary of a single Bean Validation constraint violation.
     *
     * @param property the path of the property that failed validation
     * @param message  the constraint violation message
     */
    public record Violation(
            String property,
            String message
    ) {
        static Violation from(
            jakarta.validation.ConstraintViolation<?> v
        ) {
            return new Violation(
                v.getPropertyPath().toString(),
                v.getMessage()
            );
        }
    }
}
