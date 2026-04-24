package uk.co.jasonmarston.movies.input.port;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand;

/**
 * Use-case boundary for creating a new movie.
 *
 * <p>Implementations validate and process a {@link CreateMovieCommand}, persist the
 * resulting aggregate, and return the public identifier assigned to the new movie.</p>
 *
 * @see CreateMovieCommand
 * @see PublicId
 */
public interface CreateMovieHandler {

    /**
     * Creates a new movie from the supplied command.
     *
     * @param createMovieCommand the validated command describing the movie to create
     * @return a {@link Uni} that emits the public identifier of the created movie
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if the command cannot be converted into a valid movie aggregate
     */
    Uni<PublicId> handle(final CreateMovieCommand createMovieCommand);
}
