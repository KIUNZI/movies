package uk.co.jasonmarston.movies.input.adaptor.response;

import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

/**
 * Response body describing a movie returned by the client API.
 *
 * <p>This transport model is typically produced by mapping an
 * {@link uk.co.jasonmarston.movies.input.port.view.MovieView} or domain aggregate into
 * a JSON-friendly representation for HTTP responses.</p>
 *
 * @param publicId the public identifier of the movie
 * @param version the current optimistic-locking version of the movie
 * @param title the movie title
 * @param release the movie release date
 * @param director the movie director
 * @see uk.co.jasonmarston.movies.input.port.view.MovieView
 */
public record MovieResponse(
        PublicId publicId,
        Long version,
        Title title,
        ReleaseDate release,
        Director director
) {
}
