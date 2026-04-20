package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.NotFoundException;
import uk.co.jasonmarston.movies.domain.exception.VersionMismatchException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

@Provider
public class VersionMismatchExceptionMapper
        implements ExceptionMapper<VersionMismatchException> {

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
