package uk.co.jasonmarston.movies.domain.arguments;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;

/**
 * Data carrier holding the arguments required to delete a movie aggregate.
 *
 * @param publicId the public identifier of the movie to delete; must not be {@code null}
 * @see ReadMovieArgs
 */
public record DeleteMovieArgs(
        @NotNull
        @Valid
        PublicId publicId
) {
}
