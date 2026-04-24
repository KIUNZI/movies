package uk.co.jasonmarston.movies.domain.arguments;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;

/**
 * Data carrier holding the arguments required to read a movie aggregate.
 *
 * @param publicId the public identifier of the movie to read; must not be {@code null}
 * @see DeleteMovieArgs
 */
public record ReadMovieArgs(
        @NotNull
        @Valid
        PublicId publicId
) {
}
