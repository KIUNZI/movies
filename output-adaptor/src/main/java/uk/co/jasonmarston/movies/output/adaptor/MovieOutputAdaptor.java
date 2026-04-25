package uk.co.jasonmarston.movies.output.adaptor;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.kiunzi.utility.producer.annotation.PersistenceAwareValidating;
import uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;
import uk.co.jasonmarston.movies.output.adaptor.data.MovieData;
import uk.co.jasonmarston.movies.output.adaptor.repository.MovieRepository;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

/**
 * Output adapter implementation that bridges the domain output port to persistence.
 *
 * <p>This adapter maps domain aggregates and argument objects to persistence models,
 * delegates CRUD operations to {@link MovieRepository}, and maps persistence models
 * back to domain aggregates.</p>
 *
 * @see MovieOutputPort
 * @see MovieRepository
 */
@ApplicationScoped
public class MovieOutputAdaptor implements MovieOutputPort {
    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;

    /**
     * Constructs the movie output adapter.
     *
     * @param modelMapper the persistence-aware mapper used for domain/data conversion
     * @param movieRepository the repository used to execute persistence operations
     */
    @Inject
    public MovieOutputAdaptor(
            @PersistenceAwareValidating
            final ModelMapper modelMapper,
            final MovieRepository movieRepository
    ) {
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
    }

    /**
     * Persists a new movie aggregate.
     *
     * @param movie the movie aggregate to persist
     * @return a {@link Uni} that emits the persisted movie aggregate
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.PersistenceException
     *         if persistence fails
     */
    @Override
    public Uni<Movie> createMovie(final Movie movie) {
        return movieRepository
            .createMovie(modelMapper.map(movie, MovieData.class))
            .map(movieData -> modelMapper.map(movieData, Movie.class));
    }

    /**
     * Loads a movie aggregate by public identifier.
     *
     * @param readMovieArgs the read arguments containing the target public identifier
     * @return a {@link Uni} that emits the loaded movie aggregate
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     */
    @Override
    public Uni<Movie> readMovie(final ReadMovieArgs readMovieArgs) {
        return movieRepository
            .readMovie(readMovieArgs.publicId())
            .map(movie -> modelMapper.map(movie, Movie.class));
    }

    /**
     * Updates an existing movie aggregate.
     *
     * @param movie the movie aggregate containing updated values and expected version
     * @return a {@link Uni} that emits the updated movie aggregate
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.VersionMismatchException
     *         if the supplied version does not match persisted state
     */
    @Override
    public Uni<Movie> updateMovie(final Movie movie) {
        return movieRepository
            .updateMovie(modelMapper.map(movie, MovieData.class))
            .map(movieData -> modelMapper.map(movieData, Movie.class));
    }

    /**
     * Deletes a movie aggregate by public identifier.
     *
     * @param deleteMovieArgs the delete arguments containing the target public identifier
     * @return a {@link Uni} that completes when deletion is successful
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     */
    @Override
    public Uni<Void> deleteMovie(final DeleteMovieArgs deleteMovieArgs) {
        return movieRepository
            .deleteMovie(deleteMovieArgs.publicId());
    }
}
