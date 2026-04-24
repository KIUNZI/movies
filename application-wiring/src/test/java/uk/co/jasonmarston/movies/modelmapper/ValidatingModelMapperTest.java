package uk.co.jasonmarston.movies.modelmapper;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatingModelMapperTest {
    private ValidatingModelMapper mapper;

    @BeforeEach
    void setUp() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        mapper = new ValidatingModelMapper(validator);
    }

    @Test
    void mapShouldReturnMappedObjectWhenValid() {
        Destination destination = mapper.map(new Source("Interstellar"), Destination.class);

        assertEquals("Interstellar", destination.getTitle());
    }

    @Test
    void mapShouldThrowDomainViolationExceptionWhenConstraintsFail() {
        DomainInvariantViolationException exception = assertThrows(
            DomainInvariantViolationException.class,
            () -> mapper.map(new Source(null), Destination.class)
        );

        assertFalse(exception.getViolations().isEmpty());
        assertTrue(exception.getMessage().contains("title"));
    }

    @Test
    void mapIntoExistingDestinationShouldValidateFinalState() {
        Destination destination = new Destination("Existing title");

        assertThrows(
            DomainInvariantViolationException.class,
            () -> mapper.map(new Source(null), destination)
        );
    }

    private static final class Source {
        private final String title;

        private Source(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private static final class Destination {
        @NotBlank
        private String title;

        private Destination() {
        }

        private Destination(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

