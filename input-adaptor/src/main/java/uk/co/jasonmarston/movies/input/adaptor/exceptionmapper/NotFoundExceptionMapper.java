package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.NotFoundException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

/**
 * Maps domain {@link NotFoundException} instances to HTTP 404 responses.
 *
 * <p>This mapper is used when the requested movie or related domain object cannot be
 * found by the underlying use case.</p>
 *
 * @see ErrorResponse
 */
@Provider
public class NotFoundExceptionMapper
        implements ExceptionMapper<NotFoundException> {

    /**
     * Creates a mapper for domain not-found exceptions.
     */
    public NotFoundExceptionMapper() {
    }

    /**
     * Converts the supplied domain not-found exception into an HTTP response.
     *
     * @param ex the domain not-found exception raised while processing the request
     * @return an HTTP 404 response containing an {@link ErrorResponse} body
     */
    @Override
    public Response toResponse(final NotFoundException ex) {
        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(new ErrorResponse(
                "NOT_FOUND_ERROR",
                ex.getMessage()
            ))
            .build();
    }
}

