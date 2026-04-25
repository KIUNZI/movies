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
     */
    Uni<Movie> createMovie(final Movie movie);

    /**
     * Loads a movie aggregate by its public identifier.
     *
     * @param readMovieArgs the read arguments containing the target public identifier
     * @return a {@link Uni} that emits the loaded aggregate
     */
    Uni<Movie> readMovie(final ReadMovieArgs readMovieArgs);

    /**
     * Persists updates to an existing movie aggregate.
     *
     * @param movie the aggregate containing updated values and expected version
     * @return a {@link Uni} that emits the updated aggregate
     */
    Uni<Movie> updateMovie(final Movie movie);

    /**
     * Deletes a movie aggregate by public identifier.
     *
     * @param deleteMovieArgs the delete arguments containing the target public identifier
     * @return a {@link Uni} that completes when deletion succeeds
     */
    Uni<Void> deleteMovie(final DeleteMovieArgs deleteMovieArgs);
}
