package uk.co.jasonmarston.movies.output.adaptor.repository.impl;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.kiunzi.utility.domain.exception.DataIntegrityViolationException;
import uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException;
import uk.co.jasonmarston.kiunzi.utility.domain.exception.PersistenceException;
import uk.co.jasonmarston.kiunzi.utility.domain.exception.VersionMismatchException;
import uk.co.jasonmarston.kiunzi.utility.producer.annotation.PersistenceAwareValidating;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.output.adaptor.data.MovieData;
import uk.co.jasonmarston.movies.output.adaptor.repository.MovieRepository;

import java.time.Duration;

/**
 * Reactive Panache-based implementation of {@link MovieRepository}.
 *
 * <p>This repository executes movie persistence operations in reactive transactions,
 * translates datastore outcomes into domain exceptions, and logs failures with
 * operation context.</p>
 *
 * @see MovieRepository
 * @see MovieData
 */
@ApplicationScoped
public class SimpleMovieRepository implements
        MovieRepository,
        PanacheRepositoryBase<MovieData,Long> {
    private final ModelMapper modelMapper;

    /**
     * Constructs the reactive movie repository.
     *
     * @param modelMapper the persistence-aware mapper used to copy update values into
     *                    managed entities
     */
    @Inject
    public SimpleMovieRepository(
            @PersistenceAwareValidating
            final ModelMapper modelMapper
    ) {
        this.modelMapper = modelMapper;
    }

    /**
     * Persists a new movie row inside a reactive transaction.
     *
     * @param movieData the movie data to persist
     * @return a {@link Uni} that emits the persisted movie data
     * @throws PersistenceException if persistence fails with a non-runtime cause
     */
    @Override
    public Uni<MovieData> createMovie(final MovieData movieData) {
        return Panache
            .withTransaction(() -> persist(movieData).replaceWith(movieData))
            .ifNoItem()
            .after(Duration.ofMillis(10000))
            .fail()
            .onFailure()
            .invoke(t -> logFailure("createMovie", movieData.getPublicId(), t))
            .onFailure()
            .transform(t -> {
                if (t instanceof RuntimeException re) return re;
                return new PersistenceException("Failed to persist movie", t);
            });
    }

    /**
     * Loads a movie row by public identifier.
     *
     * @param publicId the public identifier of the movie to load
     * @return a {@link Uni} that emits the loaded movie data
     * @throws NotFoundException if no movie exists for the supplied identifier
     */
    @Override
    public Uni<MovieData> readMovie(final PublicId publicId) {
        return Panache
            .withTransaction(() -> find("publicId", publicId)
                .firstResult()
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("Movie not found"))
                .onFailure()
                .invoke(t -> logFailure("readMovie", publicId, t))
            );
    }

    /**
     * Updates an existing movie row after optimistic-locking checks.
     *
     * @param movieData the candidate movie data containing updated values and version
     * @return a {@link Uni} that emits the updated movie data
     * @throws NotFoundException if no movie exists for the supplied identifier
     * @throws VersionMismatchException if the supplied version does not match current state
     */
    @Override
    public Uni<MovieData> updateMovie(final MovieData movieData) {
        return Panache
            .withTransaction(() -> find("publicId", movieData.getPublicId())
                .firstResult()
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("Movie not found"))
                .onItem()
                .transform(item -> {
                    if (!item.getVersion().equals(movieData.getVersion())) {
                        throw new VersionMismatchException(
                            "Movie was modified by another request"
                        );
                    }
                    modelMapper.map(movieData, item);
                    return item;
                })
                .onFailure(this::isOptimisticConcurrencyFailure)
                .transform(t ->
                    new VersionMismatchException(
                        "Movie was modified by another request"
                    )
                )
                .onFailure()
                .invoke(t ->
                    logFailure("updateMovie", movieData.getPublicId(), t)
                )
            );
    }

    /**
     * Deletes a movie row by public identifier.
     *
     * @param publicId the public identifier of the movie to delete
     * @return a {@link Uni} that completes when deletion succeeds
     * @throws NotFoundException if no movie exists for the supplied identifier
     * @throws DataIntegrityViolationException if delete affects more than one row
     */
    @Override
    public Uni<Void> deleteMovie(final PublicId publicId) {
        return Panache
            .withTransaction(() -> delete("publicId", publicId)
                .onItem()
                .transformToUni(deletedRows -> {
                    if (deletedRows > 1L) {
                        throw new DataIntegrityViolationException(
                                "Deleted multiple rows for one publicId"
                        );
                    }
                    if (deletedRows == 0L) {
                        throw new NotFoundException("Movie not found");
                    }
                    return Uni.createFrom().voidItem();
                })
                .onFailure()
                .invoke(t -> logFailure("deleteMovie", publicId, t))
            );
    }

    @SuppressWarnings("unused")
    private void logFailure(
            final String op,
            final Object id,
            final Throwable throwable
    ) {
        final String message = op + " failed for publicId=" + id
                + "(" + throwable.getClass().getSimpleName() + ")";
        switch (throwable) {
            case NotFoundException nfe -> Log.debug(message);
            case VersionMismatchException vme -> Log.warn(message);
            case OptimisticLockException ole -> Log.warn(message);
            default -> Log.error(message, throwable);
        }
    }

    private boolean isOptimisticConcurrencyFailure(final Throwable throwable) {
        for (Throwable current = throwable; current != null; current = current.getCause()) {
             if (current instanceof OptimisticLockException) {
                 return true;
             }
             final String className = current.getClass().getName();
             if ("org.hibernate.StaleObjectStateException".equals(className)
                     || "org.hibernate.StaleStateException".equals(className)
                     || "org.hibernate.OptimisticLockException".equals(className)) {
                 return true;
             }
         }
         return false;
     }
}
