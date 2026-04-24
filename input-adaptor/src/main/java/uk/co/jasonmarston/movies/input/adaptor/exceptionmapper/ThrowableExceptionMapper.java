package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

/**
 * Fallback exception mapper that converts otherwise-unhandled throwables into HTTP
 * 500 responses.
 *
 * <p>This mapper provides a final safety net for unexpected failures that escape the
 * more specific application exception mappers.</p>
 *
 * @see ErrorResponse
 */
@Provider
public class ThrowableExceptionMapper
        implements ExceptionMapper<Throwable> {

    /**
     * Creates the fallback mapper for otherwise-unhandled throwables.
     */
    public ThrowableExceptionMapper() {
    }

    /**
     * Converts the supplied throwable into an HTTP response.
     *
     * @param ex the unexpected throwable raised while processing the request
     * @return an HTTP 500 response containing an {@link ErrorResponse} body
     */
    @Override
    public Response toResponse(final Throwable ex) {
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                ex.getMessage()
            ))
            .build();
    }
}
