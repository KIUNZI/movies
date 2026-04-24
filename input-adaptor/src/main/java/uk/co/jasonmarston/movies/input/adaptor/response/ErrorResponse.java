package uk.co.jasonmarston.movies.input.adaptor.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Generic error payload returned when a request fails without field-level
 * validation details.
 *
 * @param code the application-specific error code identifying the failure category
 * @param message the human-readable description of the error
 * @see ValidationErrorResponse
 */
@RegisterForReflection
public record ErrorResponse(
        String code,
        String message
) {
}
