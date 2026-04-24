package uk.co.jasonmarston.movies.input.port.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

/**
 * Command carrying the information required to update an existing movie.
 *
 * <p>The command includes the movie identity, the caller's expected version for
 * optimistic locking, and the replacement values to apply.</p>
 *
 * @param publicId the public identifier of the movie to update
 * @param version the expected current version of the movie
 * @param title the replacement title
 * @param release the replacement release date
 * @param director the replacement director
 * @see uk.co.jasonmarston.movies.input.port.UpdateMovieHandler
 * @see uk.co.jasonmarston.movies.domain.arguments.UpdateMovieArgs
 */
public record UpdateMovieCommand(
        @NotNull
        @Valid
        PublicId publicId,
        @NotNull
        @Valid
        Long version,
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
