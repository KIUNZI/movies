package uk.co.jasonmarston.movies.output.adaptor;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.annotation.PersistenceAwareValidating;
import uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;
import uk.co.jasonmarston.movies.output.adaptor.data.MovieData;
import uk.co.jasonmarston.movies.output.adaptor.repository.MovieRepository;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

import java.util.Optional;

@ApplicationScoped
public class MovieOutputAdaptor implements MovieOutputPort {
    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;

    @Inject
    public MovieOutputAdaptor(
            @PersistenceAwareValidating
            final ModelMapper modelMapper,
            final MovieRepository movieRepository
    ) {
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
    }

    @Override
    public Uni<Movie> createMovie(final Movie movie) {
        return movieRepository
            .createMovie(modelMapper.map(movie, MovieData.class))
            .map(movieData -> modelMapper.map(movieData, Movie.class));
    }

    @Override
    public Uni<Movie> readMovie(final ReadMovieArgs readMovieArgs) {
        return movieRepository
            .readMovie(readMovieArgs.publicId())
            .map(movie -> modelMapper.map(movie, Movie.class));
    }

    @Override
    public Uni<Movie> updateMovie(final Movie movie) {
        return movieRepository
            .updateMovie(modelMapper.map(movie, MovieData.class))
            .map(movieData -> modelMapper.map(movieData, Movie.class));
    }

    @Override
    public Uni<Void> deleteMovie(final DeleteMovieArgs deleteMovieArgs) {
        return movieRepository
            .deleteMovie(deleteMovieArgs.publicId());
    }
}
