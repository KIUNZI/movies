package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.annotation.Validating;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.CreateMovieArgs;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

/**
 * Default implementation of the create-movie use case.
 *
 * <p>This handler maps the inbound {@link CreateMovieCommand} to
 * {@link CreateMovieArgs}, creates a validated {@link Movie} aggregate, persists it
 * through {@link MovieOutputPort}, and returns the assigned {@link PublicId}.</p>
 *
 * @see uk.co.jasonmarston.movies.input.port.CreateMovieHandler
 * @see MovieOutputPort
 */
@ApplicationScoped
public class DefaultCreateMovieHandler implements uk.co.jasonmarston.movies.input.port.CreateMovieHandler {
    private final MovieOutputPort movieOutputPort;
    private final ModelMapper modelMapper;

    /**
     * Constructs the default create-movie handler.
     *
     * @param movieOutputPort the output port used to persist newly created movies
     * @param modelMapper the validating mapper used to convert commands into domain
     *                    argument objects
     */
    @Inject
    public DefaultCreateMovieHandler(
            final MovieOutputPort movieOutputPort,
            @Validating
            final ModelMapper modelMapper
    ) {
        this.movieOutputPort = movieOutputPort;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a new movie from the supplied command.
     *
     * @param createMovieCommand the validated command describing the movie to create
     * @return a {@link Uni} that emits the public identifier of the persisted movie
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if the command cannot be converted into a valid aggregate
     */
    @Override
    public Uni<PublicId> handle(
            final CreateMovieCommand createMovieCommand
    ) {
        return Uni
            .createFrom()
            .item(createMovieCommand)
            .map(cmd ->modelMapper.map(cmd, CreateMovieArgs.class))
            .map(Movie::create)
            .flatMap(movieOutputPort::createMovie)
            .map(Movie::getPublicId);
    }
}
