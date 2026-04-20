package uk.co.jasonmarston.movies.output.adaptor.repository;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.output.adaptor.data.MovieData;

public interface MovieRepository {
    Uni<MovieData> createMovie(final MovieData movieData);
    Uni<MovieData> readMovie(final PublicId publicId);
    Uni<MovieData> updateMovie(final MovieData movieData);
    Uni<Void> deleteMovie(final PublicId publicId);
}