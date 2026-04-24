package uk.co.jasonmarston.movies.domain.arguments;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

/**
 * Data carrier holding the arguments required to update an existing movie aggregate.
 *
 * <p>Passed to {@link uk.co.jasonmarston.movies.domain.aggregate.Movie#update(UpdateMovieArgs)}
 * to replace the mutable fields of an existing {@code Movie}.</p>
 *
 * @param title    the new movie title; validated by the {@link Title} value object constraints
 * @param release  the new release date; must not be {@code null} and must be in the past
 * @param director the new director; must not be {@code null} and must satisfy
 *                 {@link Director} value object constraints
 * @see uk.co.jasonmarston.movies.domain.aggregate.Movie#update(UpdateMovieArgs)
 * @see CreateMovieArgs
 */
public record UpdateMovieArgs(
        @Valid
        Title title,
        @NotNull
        @Valid
        ReleaseDate release,
        @NotNull
        @Valid
        Director director
) {
}
