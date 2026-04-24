package uk.co.jasonmarston.movies.output.adaptor.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.jasonmarston.movies.domain.valueobject.Director;

/**
 * JPA attribute converter for persisting {@link Director} value objects as strings.
 *
 * @see Director
 */
@Converter(autoApply = true)
public class DirectorConverter implements
        AttributeConverter<Director, String> {

    /**
     * Creates a converter for {@link Director} value objects.
     */
    public DirectorConverter() {
    }

    /**
     * Converts a {@link Director} value object to its database column representation.
     *
     * @param director the value object to convert
     * @return the raw string value for persistence, or {@code null} when input is
     *         {@code null}
     */
    @Override
    public String convertToDatabaseColumn(final Director director) {
        return director == null ? null : director.getValue();
    }

    /**
     * Converts a database column value to a {@link Director} value object.
     *
     * @param director the raw column value
     * @return a {@link Director} instance, or {@code null} when input is {@code null}
     */
    @Override
    public Director convertToEntityAttribute(final String director) {
        return director  == null ? null : Director.of(director);
    }
}
