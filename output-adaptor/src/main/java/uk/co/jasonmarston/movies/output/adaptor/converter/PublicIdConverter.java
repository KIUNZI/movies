package uk.co.jasonmarston.movies.output.adaptor.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;

import java.util.UUID;

/**
 * JPA attribute converter for persisting {@link PublicId} value objects as UUIDs.
 *
 * @see PublicId
 */
@Converter(autoApply = true)
public class PublicIdConverter implements
        AttributeConverter<PublicId, UUID> {

    /**
     * Creates a converter for {@link PublicId} value objects.
     */
    public PublicIdConverter() {
    }

    /**
     * Converts a {@link PublicId} value object to its database column representation.
     *
     * @param publicId the value object to convert
     * @return the UUID value for persistence, or {@code null} when input is {@code null}
     */
    @Override
    public UUID convertToDatabaseColumn(final PublicId publicId) {
        return publicId == null ? null : publicId.getValue();
    }

    /**
     * Converts a database column value to a {@link PublicId} value object.
     *
     * @param publicId the raw UUID column value
     * @return a {@link PublicId} instance, or {@code null} when input is {@code null}
     */
    @Override
    public PublicId convertToEntityAttribute(final UUID publicId) {
        return publicId == null ? null : PublicId.of(publicId);
    }
}
