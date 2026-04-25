package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.kiunzi.utility.producer.annotation.Validating;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;
import uk.co.jasonmarston.movies.input.port.command.ReadMovieCommand;
import uk.co.jasonmarston.movies.input.port.view.MovieView;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

/**
 * Default implementation of the read-movie use case.
 *
 * <p>This handler maps a {@link ReadMovieCommand} to {@link ReadMovieArgs}, delegates
 * the lookup to {@link MovieOutputPort}, and converts the resulting aggregate into a
 * {@link MovieView} projection.</p>
 *
 * @see uk.co.jasonmarston.movies.input.port.ReadMovieHandler
 * @see MovieOutputPort
 */
@ApplicationScoped
public class DefaultReadMovieHandler implements uk.co.jasonmarston.movies.input.port.ReadMovieHandler {
    private final MovieOutputPort movieOutputPort;
    private final ModelMapper modelMapper;

    /**
     * Constructs the default read-movie handler.
     *
     * @param movieOutputPort the output port used to load movies
     * @param modelMapper the validating mapper used to convert commands and view models
     */
    @Inject
    public DefaultReadMovieHandler(
            final MovieOutputPort movieOutputPort,
            @Validating
            final ModelMapper modelMapper
    ) {
        this.movieOutputPort = movieOutputPort;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves the movie described by the supplied command.
     *
     * @param readMovieCommand the validated command identifying the movie to read
     * @return a {@link Uni} that emits the resulting movie view
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     */
    @Override
    public Uni<MovieView> handle(
            final ReadMovieCommand readMovieCommand
    ) {
        return Uni
            .createFrom()
            .item(readMovieCommand)
            .map(cmd -> modelMapper.map(cmd, ReadMovieArgs.class))
            .flatMap(movieOutputPort::readMovie)
            .map(movie -> modelMapper.map(movie, MovieView.class));
    }
}
