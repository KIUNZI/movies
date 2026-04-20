package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;
import uk.co.jasonmarston.movies.input.adaptor.response.ValidationErrorResponse;

import java.util.List;

@Provider
public class DomainInvariantViolationExceptionMapper
        implements ExceptionMapper<DomainInvariantViolationException> {

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