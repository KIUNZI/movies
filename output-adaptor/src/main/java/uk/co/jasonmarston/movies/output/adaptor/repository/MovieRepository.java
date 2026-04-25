package uk.co.jasonmarston.movies.output.adaptor.repository;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.output.adaptor.data.MovieData;

/**
 * Persistence repository contract for storing and retrieving {@link MovieData}.
 *
 * <p>Implementations encapsulate datastore interaction details for movie persistence
 * operations.</p>
 *
 * @see MovieData
 */
public interface MovieRepository {

    /**
     * Persists a new movie row.
     *
     * @param movieData the data object to persist
     * @return a {@link Uni} that emits the persisted data object
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.PersistenceException
     *         if persistence fails
     */
    Uni<MovieData> createMovie(final MovieData movieData);

    /**
     * Loads a movie by public identifier.
     *
     * @param publicId the public identifier of the movie to load
     * @return a {@link Uni} that emits the loaded data object
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     */
    Uni<MovieData> readMovie(final PublicId publicId);

    /**
     * Updates an existing movie row.
     *
     * @param movieData the data object containing updated values
     * @return a {@link Uni} that emits the updated data object
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.VersionMismatchException
     *         if optimistic-locking version checks fail
     */
    Uni<MovieData> updateMovie(final MovieData movieData);

    /**
     * Deletes a movie by public identifier.
     *
     * @param publicId the public identifier of the movie to delete
     * @return a {@link Uni} that completes when deletion is successful
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.DataIntegrityViolationException
     *         if delete results violate expected row cardinality
     */
    Uni<Void> deleteMovie(final PublicId publicId);
}
