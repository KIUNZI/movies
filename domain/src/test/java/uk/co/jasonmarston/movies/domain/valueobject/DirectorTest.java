package uk.co.jasonmarston.movies.domain.valueobject;

import org.junit.jupiter.api.Test;
import uk.co.jasonmarston.kiunzi.utility.validator.DomainInvariantViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirectorTest {

    @Test
    void shouldTrimWhitespace() {
        Director director = Director.of("  Denis Villeneuve  ");

        assertEquals("Denis Villeneuve", director.getValue());
        assertEquals("Denis Villeneuve", director.toString());
    }

    @Test
    void shouldRejectBlankValue() {
        assertThrows(
            DomainInvariantViolationException.class,
            () -> Director.of("   ")
        );
    }

    @Test
    void shouldRejectTooLongValue() {
        String longDirector = "d".repeat(255);

        assertThrows(
            DomainInvariantViolationException.class,
            () -> Director.of(longDirector)
        );
    }
}

