package uk.co.jasonmarston.movies.output.adaptor.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;

import java.util.UUID;

@Converter(autoApply = true)
public class PublicIdConverter implements
        AttributeConverter<PublicId, UUID> {
    @Override
    public UUID convertToDatabaseColumn(final PublicId publicId) {
        return publicId == null ? null : publicId.getValue();
    }

    @Override
    public PublicId convertToEntityAttribute(final UUID publicId) {
        return publicId == null ? null : PublicId.of(publicId);
    }
}
