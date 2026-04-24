package uk.co.jasonmarston.movies.input.port.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;

/**
 * Command identifying the movie that should be retrieved.
 *
 * @param publicId the public identifier of the movie to read
 * @see uk.co.jasonmarston.movies.input.port.ReadMovieHandler
 * @see uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs
 */
public record ReadMovieCommand(
        @NotNull
        @Valid
        PublicId publicId
) {
}
