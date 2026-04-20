package uk.co.jasonmarston.movies.output.adaptor.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;

import java.time.LocalDate;

@Converter(autoApply = true)
public class ReleaseDateConverter implements
        AttributeConverter<ReleaseDate, LocalDate> {
    @Override
    public LocalDate convertToDatabaseColumn(final ReleaseDate releaseDate) {
        return releaseDate == null ? null : releaseDate.getValue();
    }

    @Override
    public ReleaseDate convertToEntityAttribute(final LocalDate releaseDate) {
        return releaseDate == null ? null : ReleaseDate.of(releaseDate);
    }
}
