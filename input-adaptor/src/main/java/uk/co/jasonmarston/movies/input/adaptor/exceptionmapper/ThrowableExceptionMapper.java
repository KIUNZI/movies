package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

@Provider
public class ThrowableExceptionMapper
        implements ExceptionMapper<Throwable> {

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

