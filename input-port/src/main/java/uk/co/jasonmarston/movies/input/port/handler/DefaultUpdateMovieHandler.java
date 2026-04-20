package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.annotation.Validating;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;
import uk.co.jasonmarston.movies.domain.exception.NotFoundException;
import uk.co.jasonmarston.movies.domain.exception.VersionMismatchException;
import uk.co.jasonmarston.movies.domain.arguments.UpdateMovieArgs;
import uk.co.jasonmarston.movies.input.port.command.UpdateMovieCommand;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

@ApplicationScoped
public class DefaultUpdateMovieHandler implements uk.co.jasonmarston.movies.input.port.UpdateMovieHandler {
    private final MovieOutputPort movieOutputPort;
    private final ModelMapper modelMapper;

    @Inject
    public DefaultUpdateMovieHandler(
            final MovieOutputPort movieOutputPort,
            @Validating
            final ModelMapper modelMapper
    ) {
        this.movieOutputPort = movieOutputPort;
        this.modelMapper = modelMapper;
    }

    @Override
    public Uni<Movie> handle(
            final UpdateMovieCommand updateMovieCommand
    ) {
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
