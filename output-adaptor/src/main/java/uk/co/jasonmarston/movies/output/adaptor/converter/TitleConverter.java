package uk.co.jasonmarston.movies.output.adaptor.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

/**
 * JPA attribute converter for persisting {@link Title} value objects as strings.
 *
 * @see Title
 */
@Converter(autoApply = true)
public class TitleConverter implements
        AttributeConverter<Title, String> {

    /**
     * Creates a converter for {@link Title} value objects.
     */
    public TitleConverter() {
    }

    /**
     * Converts a {@link Title} value object to its database column representation.
     *
     * @param title the value object to convert
     * @return the raw string value for persistence, or {@code null} when input is
     *         {@code null}
     */
    @Override
    public String convertToDatabaseColumn(final Title title) {
        return title == null ? null : title.getValue();
    }

    /**
     * Converts a database column value to a {@link Title} value object.
     *
     * @param title the raw column value
     * @return a {@link Title} instance, or {@code null} when input is {@code null}
     */
    @Override
    public Title convertToEntityAttribute(final String title) {
        return title  == null ? null : Title.of(title);
    }
}
