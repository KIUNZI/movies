package uk.co.jasonmarston.movies.input.adaptor.response;

import java.util.List;

/**
 * Error payload returned when one or more validation or invariant violations must be
 * reported to the client.
 *
 * @param title the summary title of the validation failure
 * @param status the HTTP status code associated with the response
 * @param violations the individual field or property violations that caused the failure
 * @see uk.co.jasonmarston.movies.input.adaptor.exceptionmapper.DomainInvariantViolationExceptionMapper
 */
public record ValidationErrorResponse(
        String title,
        int status,
        List<Violation> violations
        ) {

    /**
     * Describes a single validation failure within a {@link ValidationErrorResponse}.
     *
     * @param field the field or property path associated with the violation
     * @param message the validation message for the violation
     */
    public record Violation(
            String field,
            String message
    ) {}
}
