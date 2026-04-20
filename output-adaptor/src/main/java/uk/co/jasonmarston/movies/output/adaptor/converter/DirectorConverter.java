package uk.co.jasonmarston.movies.output.adaptor.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.jasonmarston.movies.domain.valueobject.Director;

@Converter(autoApply = true)
public class DirectorConverter implements
        AttributeConverter<Director, String> {
    @Override
    public String convertToDatabaseColumn(final Director director) {
        return director == null ? null : director.getValue();
    }

    @Override
    public Director convertToEntityAttribute(final String director) {
        return director  == null ? null : Director.of(director);
    }
}
