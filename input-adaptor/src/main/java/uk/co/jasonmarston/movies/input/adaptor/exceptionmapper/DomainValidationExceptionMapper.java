package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.DomainValidationException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

/**
 * Maps {@link DomainValidationException} instances to HTTP 400 responses.
 *
 * <p>This mapper is used when a request is syntactically valid but fails domain-level
 * validation rules expressed by the input-port layer.</p>
 *
 * @see ErrorResponse
 */
@Provider
public class DomainValidationExceptionMapper
        implements ExceptionMapper<DomainValidationException> {

    /**
     * Creates a mapper for domain validation exceptions.
     */
    public DomainValidationExceptionMapper() {
    }

    /**
     * Converts the supplied domain validation exception into an HTTP response.
     *
     * @param ex the domain validation exception raised while processing the request
     * @return an HTTP 400 response containing an {@link ErrorResponse} body
     */
    @Override
    public Response toResponse(final DomainValidationException ex) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse(
                "DOMAIN_VALIDATION_ERROR",
                ex.getMessage()
            ))
            .build();
    }
}
