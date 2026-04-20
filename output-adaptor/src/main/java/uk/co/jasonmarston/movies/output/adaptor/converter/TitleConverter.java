package uk.co.jasonmarston.movies.output.adaptor.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

@Converter(autoApply = true)
public class TitleConverter implements
        AttributeConverter<Title, String> {
    @Override
    public String convertToDatabaseColumn(final Title title) {
        return title == null ? null : title.getValue();
    }

    @Override
    public Title convertToEntityAttribute(final String title) {
        return title  == null ? null : Title.of(title);
    }
}
