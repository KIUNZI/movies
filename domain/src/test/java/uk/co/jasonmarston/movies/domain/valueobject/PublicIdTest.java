package uk.co.jasonmarston.movies.domain.valueobject;

import org.junit.jupiter.api.Test;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PublicIdTest {

    @Test
    void shouldWrapUuidValue() {
        UUID value = UUID.fromString("77a7b095-f1d2-4f43-a49f-00d41666d4f6");

        PublicId publicId = PublicId.of(value);

        assertEquals(value, publicId.getValue());
        assertEquals(value.toString(), publicId.toString());
    }

    @Test
    void shouldRejectNullUuid() {
        assertThrows(
            DomainInvariantViolationException.class,
            () -> PublicId.of(null)
        );
    }
}

