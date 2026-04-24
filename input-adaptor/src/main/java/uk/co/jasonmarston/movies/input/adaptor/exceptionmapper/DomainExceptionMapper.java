package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.DomainException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

/**
 * Maps unclassified {@link DomainException} instances to HTTP 500 responses.
 *
 * <p>This mapper provides a fallback transport representation for domain-level
 * failures that are not handled by a more specific exception mapper.</p>
 *
 * @see ErrorResponse
 */
@Provider
public class DomainExceptionMapper
        implements ExceptionMapper<DomainException> {
    /**
     * Creates a mapper for unclassified domain exceptions.
     */
    public DomainExceptionMapper() {
    }

    /**
     * Converts the supplied domain exception into an HTTP response.
     *
     * @param ex the domain exception raised while processing the request
     * @return an HTTP 500 response containing an {@link ErrorResponse} body
     */
    @Override
    public Response toResponse(final DomainException ex) {
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new ErrorResponse(
                "DOMAIN_ERROR",
                ex.getMessage()
            ))
            .build();
    }
}

