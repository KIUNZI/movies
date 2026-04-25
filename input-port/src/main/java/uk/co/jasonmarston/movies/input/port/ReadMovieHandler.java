package uk.co.jasonmarston.movies.input.port;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.input.port.command.ReadMovieCommand;
import uk.co.jasonmarston.movies.input.port.view.MovieView;

/**
 * Use-case boundary for retrieving a movie by its public identifier.
 *
 * <p>Implementations translate a {@link ReadMovieCommand} into the query arguments
 * needed to fetch a movie and return the resulting {@link MovieView} projection.</p>
 *
 * @see ReadMovieCommand
 * @see MovieView
 */
public interface ReadMovieHandler {

    /**
     * Retrieves the movie described by the supplied command.
     *
     * @param readMovieCommand the validated command identifying the movie to read
     * @return a {@link Uni} that emits the resulting movie view
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     */
    Uni<MovieView> handle(final ReadMovieCommand readMovieCommand);
}
