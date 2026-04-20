package uk.co.jasonmarston.movies.output.port;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;

public interface MovieOutputPort {
    Uni<Movie> createMovie(final Movie movie);
    Uni<Movie> readMovie(final ReadMovieArgs readMovieArgs);
    Uni<Movie> updateMovie(final Movie movie);
    Uni<Void> deleteMovie(final DeleteMovieArgs deleteMovieArgs);
}
