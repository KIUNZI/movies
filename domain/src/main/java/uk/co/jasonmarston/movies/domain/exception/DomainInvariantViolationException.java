package uk.co.jasonmarston.movies.domain.exception;

import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class DomainInvariantViolationException
        extends RuntimeException {

    private final Set<Violation> violations;

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
