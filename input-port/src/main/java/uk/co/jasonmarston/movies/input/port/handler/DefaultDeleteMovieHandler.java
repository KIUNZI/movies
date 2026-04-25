package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.kiunzi.utility.producer.annotation.Validating;
import uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs;
import uk.co.jasonmarston.movies.input.port.DeleteMovieHandler;
import uk.co.jasonmarston.movies.input.port.command.DeleteMovieCommand;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

/**
 * Default implementation of the delete-movie use case.
 *
 * <p>This handler maps the inbound {@link DeleteMovieCommand} to
 * {@link DeleteMovieArgs} and delegates the delete operation to
 * {@link MovieOutputPort}.</p>
 *
 * @see DeleteMovieHandler
 * @see MovieOutputPort
 */
@ApplicationScoped
public class DefaultDeleteMovieHandler implements DeleteMovieHandler {
    private final MovieOutputPort movieOutputPort;
    private final ModelMapper modelMapper;

    /**
     * Constructs the default delete-movie handler.
     *
     * @param movieOutputPort the output port used to delete movies
     * @param modelMapper the validating mapper used to convert commands into domain
     *                    argument objects
     */
    @Inject
    public DefaultDeleteMovieHandler(
            final MovieOutputPort movieOutputPort,
            @Validating
            final ModelMapper modelMapper
    ) {
        this.movieOutputPort = movieOutputPort;
        this.modelMapper = modelMapper;
    }

    /**
     * Deletes the movie described by the supplied command.
     *
     * @param deleteMovieCommand the validated command identifying the movie to delete
     * @return a {@link Uni} that completes when the movie has been deleted
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     */
    @Override
    public Uni<Void> handle(
            final DeleteMovieCommand deleteMovieCommand
    ) {
        return Uni
            .createFrom()
            .item(deleteMovieCommand)
            .map(cmd -> modelMapper.map(cmd, DeleteMovieArgs.class))
            .flatMap(movieOutputPort::deleteMovie);
    }
}
