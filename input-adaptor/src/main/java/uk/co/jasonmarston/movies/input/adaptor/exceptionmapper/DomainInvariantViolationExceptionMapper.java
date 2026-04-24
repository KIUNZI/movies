package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;
import uk.co.jasonmarston.movies.input.adaptor.response.ValidationErrorResponse;

import java.util.List;

/**
 * Maps {@link DomainInvariantViolationException} instances to HTTP 409 responses
 * with detailed validation errors.
 *
 * <p>Each domain violation is converted into a
 * {@link ValidationErrorResponse.Violation} entry so clients can identify the field
 * or property that caused the conflict.</p>
 *
 * @see ValidationErrorResponse
 */
@Provider
public class DomainInvariantViolationExceptionMapper
        implements ExceptionMapper<DomainInvariantViolationException> {

    /**
     * Creates a mapper for domain invariant violations.
     */
    public DomainInvariantViolationExceptionMapper() {
    }

    /**
     * Converts the supplied domain invariant violation into an HTTP response.
     *
     * @param ex the domain invariant violation raised while processing the request
     * @return an HTTP 409 response containing a {@link ValidationErrorResponse} body
     */
    @Override
    public Response toResponse(final DomainInvariantViolationException ex) {
        final List<ValidationErrorResponse.Violation> violations = ex
            .getViolations()
            .stream()
            .map(v -> new ValidationErrorResponse.Violation(
                v.property(),
                v.message()
            ))
            .toList();

        final ValidationErrorResponse body =
            new ValidationErrorResponse(
                "Domain Invariant Violation",
                Response.Status.CONFLICT.getStatusCode(),
                violations
            );

        return Response
            .status(Response.Status.CONFLICT)
            .entity(body)
            .build();
    }
}
