package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.VersionMismatchException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

/**
 * Maps {@link VersionMismatchException} instances to HTTP 409 conflict responses.
 *
 * <p>This mapper is used when optimistic locking detects that the client's expected
 * version does not match the current stored version of the movie.</p>
 *
 * @see ErrorResponse
 */
@Provider
public class VersionMismatchExceptionMapper
        implements ExceptionMapper<VersionMismatchException> {

    /**
     * Creates a mapper for optimistic-locking version mismatches.
     */
    public VersionMismatchExceptionMapper() {
    }

    /**
     * Converts the supplied version-mismatch exception into an HTTP response.
     *
     * @param ex the optimistic-locking exception raised while processing the request
     * @return an HTTP 409 response containing an {@link ErrorResponse} body
     */
    @Override
    public Response toResponse(final VersionMismatchException ex) {
        return Response
            .status(Response.Status.CONFLICT)
            .entity(new ErrorResponse(
                "VERSION_MISMATCH_ERROR",
                ex.getMessage()
            ))
            .build();
    }
}
