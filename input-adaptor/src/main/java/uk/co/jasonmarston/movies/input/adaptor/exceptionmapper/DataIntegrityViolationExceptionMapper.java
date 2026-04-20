package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.DataIntegrityViolationException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

@Provider
public class DataIntegrityViolationExceptionMapper
        implements ExceptionMapper<DataIntegrityViolationException> {

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

