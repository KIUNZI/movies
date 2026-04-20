package uk.co.jasonmarston.movies.output.adaptor.repository.impl;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.annotation.PersistenceAwareValidating;
import uk.co.jasonmarston.movies.domain.exception.DataIntegrityViolationException;
import uk.co.jasonmarston.movies.domain.exception.PersistenceException;
import uk.co.jasonmarston.movies.domain.exception.NotFoundException;
import uk.co.jasonmarston.movies.domain.exception.VersionMismatchException;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.output.adaptor.data.MovieData;
import uk.co.jasonmarston.movies.output.adaptor.repository.MovieRepository;

import java.time.Duration;

@ApplicationScoped
public class SimpleMovieRepository implements
        MovieRepository,
        PanacheRepositoryBase<MovieData,Long> {
    private final ModelMapper modelMapper;

    @Inject
    public SimpleMovieRepository(
            @PersistenceAwareValidating
            final ModelMapper modelMapper
    ) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Uni<MovieData> createMovie(final MovieData movieData) {
        return Panache
            .withTransaction(() -> persist(movieData)
                .replaceWith(movieData)
            )
            .ifNoItem()
            .after(Duration.ofMillis(10000))
            .fail()
            .onFailure()
            .invoke(t -> logError("createMovie", movieData.getPublicId()))
            .onFailure()
            .transform(t -> {
                if (t instanceof RuntimeException re) return re;
                return new PersistenceException("Failed to persist movie", t);
            });
    }

    @Override
    public Uni<MovieData> readMovie(final PublicId publicId) {
        return Panache
            .withTransaction(() -> find("publicId", publicId)
                .firstResult()
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("Movie not found"))
                .onFailure()
                .invoke(t -> logError("readMovie", publicId))
            );
    }

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
                .onFailure()
                .invoke(t -> logError("updateMovie", movieData.getPublicId()))
            );
    }

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
                .invoke(t -> logError("deleteMovie", publicId))
            );
    }

    private void logError(
            final String op,
            final Object id
    ) {
        Log.error(op + " failed for publicId=" + id);
    }
}
