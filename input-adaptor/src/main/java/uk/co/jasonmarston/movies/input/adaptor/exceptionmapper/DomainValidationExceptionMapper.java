package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uk.co.jasonmarston.movies.domain.exception.DomainValidationException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;

@Provider
public class DomainValidationExceptionMapper
        implements ExceptionMapper<DomainValidationException> {

    @Override
    public Response toResponse(final DomainValidationException ex) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse(
                "DOMAIN_VALIDATION_ERROR",
                ex.getMessage()
            ))
            .build();
    }
}

