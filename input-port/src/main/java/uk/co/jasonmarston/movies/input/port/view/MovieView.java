package uk.co.jasonmarston.movies.input.port.view;

import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

public record MovieView(
        PublicId publicId,
        Long version,
        Title title,
        ReleaseDate release,
        Director director
) {
}
