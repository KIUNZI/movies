package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.PersistenceException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

@Provider
public class PersistenceExceptionMapper
        implements ExceptionMapper<PersistenceException> {

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

