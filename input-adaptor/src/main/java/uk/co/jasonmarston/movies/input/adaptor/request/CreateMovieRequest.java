package uk.co.jasonmarston.movies.input.adaptor.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

/**
 * Request body used to create a new movie through the client API.
 *
 * <p>The request is validated with Bean Validation before being mapped to a
 * {@link uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand} for the
 * input-port layer.</p>
 *
 * @param title the proposed movie title
 * @param release the movie release date
 * @param director the movie director
 * @see uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand
 * @see uk.co.jasonmarston.movies.input.adaptor.resource.CreateMovieResource
 */
public record CreateMovieRequest(
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
