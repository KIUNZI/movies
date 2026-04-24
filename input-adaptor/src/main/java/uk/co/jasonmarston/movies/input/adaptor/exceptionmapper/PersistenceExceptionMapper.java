package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.PersistenceException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

/**
 * Maps {@link PersistenceException} instances to HTTP 500 responses.
 *
 * <p>This mapper exposes persistence-layer failures as a generic server error to the
 * client while preserving the exception message in the response body.</p>
 *
 * @see ErrorResponse
 */
@Provider
public class PersistenceExceptionMapper
        implements ExceptionMapper<PersistenceException> {

    /**
     * Creates a mapper for persistence exceptions.
     */
    public PersistenceExceptionMapper() {
    }

    /**
     * Converts the supplied persistence exception into an HTTP response.
     *
     * @param ex the persistence exception raised while processing the request
     * @return an HTTP 500 response containing an {@link ErrorResponse} body
     */
    @Override
    public Response toResponse(final PersistenceException ex) {
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new ErrorResponse(
                "PERSISTENCE_ERROR",
                ex.getMessage()
            ))
            .build();
    }
}
