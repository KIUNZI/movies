package uk.co.jasonmarston.movies.output.adaptor.converter;

import org.junit.jupiter.api.Test;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConverterTest {

    @Test
    void titleConverterShouldConvertBothWaysAndHandleNulls() {
        TitleConverter converter = new TitleConverter();

        assertEquals("Arrival", converter.convertToDatabaseColumn(Title.of("Arrival")));
        assertEquals("Arrival", converter.convertToEntityAttribute("Arrival").getValue());
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void directorConverterShouldConvertBothWaysAndHandleNulls() {
        DirectorConverter converter = new DirectorConverter();

        assertEquals("Denis Villeneuve", converter.convertToDatabaseColumn(Director.of("Denis Villeneuve")));
        assertEquals("Denis Villeneuve", converter.convertToEntityAttribute("Denis Villeneuve").getValue());
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void releaseDateConverterShouldConvertBothWaysAndHandleNulls() {
        ReleaseDateConverter converter = new ReleaseDateConverter();
        LocalDate date = LocalDate.of(2016, 9, 2);

        assertEquals(date, converter.convertToDatabaseColumn(ReleaseDate.of(date)));
        assertEquals(date, converter.convertToEntityAttribute(date).getValue());
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void publicIdConverterShouldConvertBothWaysAndHandleNulls() {
        PublicIdConverter converter = new PublicIdConverter();
        UUID id = UUID.fromString("77a7b095-f1d2-4f43-a49f-00d41666d4f6");

        assertEquals(id, converter.convertToDatabaseColumn(PublicId.of(id)));
        assertEquals(id, converter.convertToEntityAttribute(id).getValue());
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
    }
}

