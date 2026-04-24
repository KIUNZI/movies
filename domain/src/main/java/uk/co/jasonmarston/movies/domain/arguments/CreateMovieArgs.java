package uk.co.jasonmarston.movies.domain.arguments;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

/**
 * Data carrier holding the arguments required to create a new movie aggregate.
 *
 * <p>Passed to {@link uk.co.jasonmarston.movies.domain.aggregate.Movie#create(CreateMovieArgs)}
 * to supply the initial state for a new {@code Movie}.</p>
 *
 * @param title    the movie title; validated by the {@link Title} value object constraints
 * @param release  the release date; must not be {@code null} and must be in the past
 * @param director the director; must not be {@code null} and must satisfy
 *                 {@link Director} value object constraints
 * @see uk.co.jasonmarston.movies.domain.aggregate.Movie#create(CreateMovieArgs)
 * @see UpdateMovieArgs
 */
public record CreateMovieArgs(
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
