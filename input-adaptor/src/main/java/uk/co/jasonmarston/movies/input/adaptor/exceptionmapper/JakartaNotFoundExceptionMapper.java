package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

/**
 * Maps Jakarta REST {@link NotFoundException} instances to HTTP 404 responses.
 *
 * <p>This mapper covers routing-level or resource-level not-found conditions raised
 * by the Jakarta REST runtime.</p>
 *
 * @see ErrorResponse
 */
@Provider
public class JakartaNotFoundExceptionMapper
        implements ExceptionMapper<NotFoundException> {

    /**
     * Creates a mapper for Jakarta REST not-found exceptions.
     */
    public JakartaNotFoundExceptionMapper() {
    }

    /**
     * Converts the supplied Jakarta REST not-found exception into an HTTP response.
     *
     * @param ex the Jakarta REST not-found exception raised while processing the request
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
