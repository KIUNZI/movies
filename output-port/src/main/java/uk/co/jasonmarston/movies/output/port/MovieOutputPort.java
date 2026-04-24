package uk.co.jasonmarston.movies.output.port;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;

/**
 * Outbound persistence contract for movie aggregates.
 *
 * <p>Implementations provide the data-store interaction required by application
 * handlers while preserving domain-level argument and aggregate models at the API
 * boundary.</p>
 *
 * @see Movie
 * @see ReadMovieArgs
 * @see DeleteMovieArgs
 */
public interface MovieOutputPort {

    /**
     * Persists a newly created movie aggregate.
     *
     * @param movie the movie aggregate to persist
     * @return a {@link Uni} that emits the persisted aggregate
     * @throws uk.co.jasonmarston.movies.domain.exception.PersistenceException
     *         if persistence fails
     */
    Uni<Movie> createMovie(final Movie movie);

    /**
     * Loads a movie aggregate by its public identifier.
     *
     * @param readMovieArgs the read arguments containing the target public identifier
     * @return a {@link Uni} that emits the loaded aggregate
     * @throws uk.co.jasonmarston.movies.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     */
    Uni<Movie> readMovie(final ReadMovieArgs readMovieArgs);

    /**
     * Persists updates to an existing movie aggregate.
     *
     * @param movie the aggregate containing updated values and expected version
     * @return a {@link Uni} that emits the updated aggregate
     * @throws uk.co.jasonmarston.movies.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     * @throws uk.co.jasonmarston.movies.domain.exception.VersionMismatchException
     *         if optimistic-locking checks fail
     */
    Uni<Movie> updateMovie(final Movie movie);

    /**
     * Deletes a movie aggregate by public identifier.
     *
     * @param deleteMovieArgs the delete arguments containing the target public identifier
     * @return a {@link Uni} that completes when deletion succeeds
     * @throws uk.co.jasonmarston.movies.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     * @throws uk.co.jasonmarston.movies.domain.exception.DataIntegrityViolationException
     *         if the delete operation violates expected row cardinality
     */
    Uni<Void> deleteMovie(final DeleteMovieArgs deleteMovieArgs);
}
