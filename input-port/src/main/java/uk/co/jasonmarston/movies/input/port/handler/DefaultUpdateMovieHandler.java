package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.annotation.Validating;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;
import uk.co.jasonmarston.movies.domain.exception.VersionMismatchException;
import uk.co.jasonmarston.movies.domain.arguments.UpdateMovieArgs;
import uk.co.jasonmarston.movies.input.port.command.UpdateMovieCommand;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

/**
 * Default implementation of the update-movie use case.
 *
 * <p>This handler loads the current movie, checks the caller's expected version for
 * optimistic locking, applies the requested updates, and persists the modified
 * aggregate through {@link MovieOutputPort}.</p>
 *
 * @see uk.co.jasonmarston.movies.input.port.UpdateMovieHandler
 * @see MovieOutputPort
 */
@ApplicationScoped
public class DefaultUpdateMovieHandler implements uk.co.jasonmarston.movies.input.port.UpdateMovieHandler {
    private final MovieOutputPort movieOutputPort;
    private final ModelMapper modelMapper;

    /**
     * Constructs the default update-movie handler.
     *
     * @param movieOutputPort the output port used to load and persist movies
     * @param modelMapper the validating mapper used to convert commands into domain
     *                    argument objects
     */
    @Inject
    public DefaultUpdateMovieHandler(
            final MovieOutputPort movieOutputPort,
            @Validating
            final ModelMapper modelMapper
    ) {
        this.movieOutputPort = movieOutputPort;
        this.modelMapper = modelMapper;
    }

    /**
     * Updates the movie described by the supplied command.
     *
     * @param updateMovieCommand the validated command containing the target movie,
     *                           expected version, and replacement values
     * @return a {@link Uni} that emits the updated persisted aggregate
     * @throws uk.co.jasonmarston.movies.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     * @throws VersionMismatchException
     *         if the supplied version does not match the persisted version
     */
    @Override
    public Uni<Movie> handle(
            final UpdateMovieCommand updateMovieCommand
    ) {
        /*
         * Data carrier for context.
         */
        record Context(
                ReadMovieArgs readMovieArgs,
                UpdateMovieArgs updateMovieArgs,
                Long version
        ) {
        }
        return Uni
            .createFrom()
            .item(updateMovieCommand)
            .map(cmd -> new Context(
                modelMapper.map(cmd, ReadMovieArgs.class),
                modelMapper.map(cmd, UpdateMovieArgs.class),
                cmd.version()
            ))
            .flatMap(ctx -> movieOutputPort
                .readMovie(ctx.readMovieArgs())
                .map(movie -> {
                    if (!movie.getVersion().equals(ctx.version())) {
                        throw new VersionMismatchException(
                            "Movie was modified by another request"
                        );
                    }
                    movie.update(ctx.updateMovieArgs());
                    return movie;
                })
            )
            .flatMap(movieOutputPort::updateMovie);
    }
}
