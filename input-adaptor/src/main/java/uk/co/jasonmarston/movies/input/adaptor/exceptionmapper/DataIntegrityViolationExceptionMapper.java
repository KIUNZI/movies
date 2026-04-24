package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.DataIntegrityViolationException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

/**
 * Maps {@link DataIntegrityViolationException} instances to HTTP 500 responses.
 *
 * <p>This mapper is typically used for persistence-layer integrity failures that do
 * not have a more specific client-facing representation.</p>
 *
 * @see ErrorResponse
 */
@Provider
public class DataIntegrityViolationExceptionMapper
        implements ExceptionMapper<DataIntegrityViolationException> {

    /**
     * Creates a mapper for data-integrity violations.
     */
    public DataIntegrityViolationExceptionMapper() {
    }

    /**
     * Converts the supplied data-integrity exception into an HTTP response.
     *
     * @param ex the data-integrity exception raised while processing the request
     * @return an HTTP 500 response containing an {@link ErrorResponse} body
     */
    @Override
    public Response toResponse(final DataIntegrityViolationException ex) {
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new ErrorResponse(
                "DATA_INTEGRITY_VIOLATION_ERROR",
                ex.getMessage()
            ))
            .build();
    }
}

