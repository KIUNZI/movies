package uk.co.jasonmarston.movies.input.port.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;

/**
 * Command identifying the movie that should be deleted.
 *
 * @param publicId the public identifier of the movie to delete
 * @see uk.co.jasonmarston.movies.input.port.DeleteMovieHandler
 * @see uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs
 */
public record DeleteMovieCommand(
        @NotNull
        @Valid
        PublicId publicId
) {
}
