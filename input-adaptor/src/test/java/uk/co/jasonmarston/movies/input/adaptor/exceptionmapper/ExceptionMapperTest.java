package uk.co.jasonmarston.movies.input.adaptor.exceptionmapper;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.jasonmarston.movies.domain.exception.DataIntegrityViolationException;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;
import uk.co.jasonmarston.movies.domain.exception.DomainValidationException;
import uk.co.jasonmarston.movies.domain.exception.PersistenceException;
import uk.co.jasonmarston.movies.domain.exception.VersionMismatchException;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;
import uk.co.jasonmarston.movies.input.adaptor.response.ValidationErrorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ExceptionMapperTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void domainValidationExceptionMapperShouldReturnBadRequest() {
        Response response = new DomainValidationExceptionMapper()
            .toResponse(new DomainValidationException("validation failed"));

        assertError(response, Response.Status.BAD_REQUEST, "DOMAIN_VALIDATION_ERROR", "validation failed");
    }

    @Test
    void domainInvariantViolationExceptionMapperShouldReturnConflictWithViolations() {
        var violations = validator.validate(new InvalidPayload());

        Response response = new DomainInvariantViolationExceptionMapper()
            .toResponse(new DomainInvariantViolationException(violations));

        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        ValidationErrorResponse body = assertInstanceOf(ValidationErrorResponse.class, response.getEntity());
        assertEquals("Domain Invariant Violation", body.title());
        assertEquals(Response.Status.CONFLICT.getStatusCode(), body.status());
        assertEquals(1, body.violations().size());
        assertEquals("name", body.violations().getFirst().field());
        assertEquals("must not be blank", body.violations().getFirst().message());
    }

    @Test
    void notFoundExceptionMapperShouldReturnNotFound() {
        Response response = new NotFoundExceptionMapper()
            .toResponse(new uk.co.jasonmarston.movies.domain.exception.NotFoundException("movie missing"));

        assertError(response, Response.Status.NOT_FOUND, "NOT_FOUND_ERROR", "movie missing");
    }

    @Test
    void jakartaNotFoundExceptionMapperShouldReturnNotFound() {
        Response response = new JakartaNotFoundExceptionMapper()
            .toResponse(new NotFoundException("route missing"));

        assertError(response, Response.Status.NOT_FOUND, "NOT_FOUND_ERROR", "route missing");
    }

    @Test
    void versionMismatchExceptionMapperShouldReturnConflict() {
        Response response = new VersionMismatchExceptionMapper()
            .toResponse(new VersionMismatchException("version conflict"));

        assertError(response, Response.Status.CONFLICT, "VERSION_MISMATCH_ERROR", "version conflict");
    }

    @Test
    void persistenceExceptionMapperShouldReturnServerError() {
        Response response = new PersistenceExceptionMapper()
            .toResponse(new PersistenceException("db unavailable"));

        assertError(response, Response.Status.INTERNAL_SERVER_ERROR, "PERSISTENCE_ERROR", "db unavailable");
    }

    @Test
    void dataIntegrityExceptionMapperShouldReturnServerError() {
        Response response = new DataIntegrityViolationExceptionMapper()
            .toResponse(new DataIntegrityViolationException("duplicate key"));

        assertError(
            response,
            Response.Status.INTERNAL_SERVER_ERROR,
            "DATA_INTEGRITY_VIOLATION_ERROR",
            "duplicate key"
        );
    }

    @Test
    void domainExceptionMapperShouldReturnServerError() {
        Response response = new DomainExceptionMapper()
            .toResponse(new DomainValidationException("domain issue"));

        assertError(response, Response.Status.INTERNAL_SERVER_ERROR, "DOMAIN_ERROR", "domain issue");
    }

    @Test
    void throwableExceptionMapperShouldReturnServerError() {
        Response response = new ThrowableExceptionMapper()
            .toResponse(new IllegalStateException("unexpected"));

        assertError(response, Response.Status.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "unexpected");
    }

    private static void assertError(Response response, Response.Status status, String code, String message) {
        assertEquals(status.getStatusCode(), response.getStatus());
        ErrorResponse body = assertInstanceOf(ErrorResponse.class, response.getEntity());
        assertEquals(code, body.code());
        assertEquals(message, body.message());
    }

    private static final class InvalidPayload {
        @NotBlank
        private final String name = "";
    }
}


