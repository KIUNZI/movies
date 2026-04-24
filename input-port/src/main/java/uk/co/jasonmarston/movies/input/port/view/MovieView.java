package uk.co.jasonmarston.movies.input.port.view;

import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

/**
 * Read-side projection of a movie returned by query-oriented use cases.
 *
 * @param publicId the public identifier of the movie
 * @param version the current optimistic-locking version of the movie
 * @param title the movie title
 * @param release the movie release date
 * @param director the movie director
 * @see uk.co.jasonmarston.movies.input.port.ReadMovieHandler
 */
public record MovieView(
        PublicId publicId,
        Long version,
        Title title,
        ReleaseDate release,
        Director director
) {
}
