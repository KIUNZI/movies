package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.DomainException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

@Provider
public class DomainExceptionMapper
        implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(final DomainException ex) {
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new ErrorResponse(
                "DOMAIN_ERROR",
                ex.getMessage()
            ))
            .build();
    }
}

