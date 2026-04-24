package uk.co.jasonmarston.movies.domain.valueobject;

import org.junit.jupiter.api.Test;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TitleTest {

    @Test
    void shouldTrimWhitespace() {
        Title title = Title.of("  Arrival  ");

        assertEquals("Arrival", title.getValue());
        assertEquals("Arrival", title.toString());
    }

    @Test
    void shouldRejectBlankValue() {
        assertThrows(
            DomainInvariantViolationException.class,
            () -> Title.of("   ")
        );
    }

    @Test
    void shouldRejectTooLongValue() {
        String longTitle = "a".repeat(255);

        assertThrows(
            DomainInvariantViolationException.class,
            () -> Title.of(longTitle)
        );
    }
}

