package uk.co.jasonmarston.movies.domain.valueobject;

import org.junit.jupiter.api.Test;
import uk.co.jasonmarston.kiunzi.utility.validator.DomainInvariantViolationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReleaseDateTest {

    @Test
    void shouldAcceptPastDate() {
        LocalDate value = LocalDate.of(1999, 10, 15);

        ReleaseDate releaseDate = ReleaseDate.of(value);

        assertEquals(value, releaseDate.getValue());
        assertEquals("1999-10-15", releaseDate.toString());
    }

    @Test
    void shouldRejectTodayDate() {
        assertThrows(
            DomainInvariantViolationException.class,
            () -> ReleaseDate.of(LocalDate.now())
        );
    }

    @Test
    void shouldRejectNullDate() {
        assertThrows(
            DomainInvariantViolationException.class,
            () -> ReleaseDate.of(null)
        );
    }
}

