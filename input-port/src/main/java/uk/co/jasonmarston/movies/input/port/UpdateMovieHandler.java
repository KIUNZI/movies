package uk.co.jasonmarston.movies.input.port;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.input.port.command.UpdateMovieCommand;

/**
 * Use-case boundary for updating an existing movie.
 *
 * <p>Implementations load the current aggregate, verify the expected version, apply
 * the requested changes, and persist the updated aggregate.</p>
 *
 * @see UpdateMovieCommand
 * @see Movie
 */
public interface UpdateMovieHandler {

    /**
     * Updates the movie described by the supplied command.
     *
     * @param updateMovieCommand the validated command containing the target movie,
     *                           expected version, and replacement values
     * @return a {@link Uni} that emits the updated movie aggregate
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.VersionMismatchException
     *         if the supplied version does not match the persisted version
     */
    Uni<Movie> handle(final UpdateMovieCommand updateMovieCommand);
}
