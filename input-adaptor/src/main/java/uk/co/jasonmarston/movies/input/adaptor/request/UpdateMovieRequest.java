package uk.co.jasonmarston.movies.input.adaptor.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

/**
 * Request body used to update an existing movie through the client API.
 *
 * <p>The payload supplies the movie identifier, the expected aggregate version for
 * optimistic locking, and the replacement movie details. It is validated before
 * being mapped to an
 * {@link uk.co.jasonmarston.movies.input.port.command.UpdateMovieCommand}.</p>
 *
 * @param publicId the public identifier of the movie to update
 * @param version the expected current version of the movie
 * @param title the replacement movie title
 * @param release the replacement release date
 * @param director the replacement director
 * @see uk.co.jasonmarston.movies.input.port.command.UpdateMovieCommand
 * @see uk.co.jasonmarston.movies.input.adaptor.resource.UpdateMovieResource
 */
public record UpdateMovieRequest(
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
