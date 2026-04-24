package uk.co.jasonmarston.movies.input.port.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

/**
 * Command carrying the information required to create a new movie.
 *
 * <p>This immutable command is typically produced by a transport adapter and passed to
 * {@link uk.co.jasonmarston.movies.input.port.CreateMovieHandler}.</p>
 *
 * @param title the title for the new movie
 * @param release the release date for the new movie
 * @param director the director of the new movie
 * @see uk.co.jasonmarston.movies.input.port.CreateMovieHandler
 * @see uk.co.jasonmarston.movies.domain.arguments.CreateMovieArgs
 */
public record CreateMovieCommand(
        @NotNull
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
