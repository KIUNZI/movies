package uk.co.jasonmarston.movies.output.adaptor.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;

import java.time.LocalDate;

/**
 * JPA attribute converter for persisting {@link ReleaseDate} value objects as dates.
 *
 * @see ReleaseDate
 */
@Converter(autoApply = true)
public class ReleaseDateConverter implements
        AttributeConverter<ReleaseDate, LocalDate> {

    /**
     * Creates a converter for {@link ReleaseDate} value objects.
     */
    public ReleaseDateConverter() {
    }

    /**
     * Converts a {@link ReleaseDate} value object to its database column value.
     *
     * @param releaseDate the value object to convert
     * @return the {@link LocalDate} value for persistence, or {@code null} when input
     *         is {@code null}
     */
    @Override
    public LocalDate convertToDatabaseColumn(final ReleaseDate releaseDate) {
        return releaseDate == null ? null : releaseDate.getValue();
    }

    /**
     * Converts a database column value to a {@link ReleaseDate} value object.
     *
     * @param releaseDate the raw date column value
     * @return a {@link ReleaseDate} instance, or {@code null} when input is {@code null}
     */
    @Override
    public ReleaseDate convertToEntityAttribute(final LocalDate releaseDate) {
        return releaseDate == null ? null : ReleaseDate.of(releaseDate);
    }
}
