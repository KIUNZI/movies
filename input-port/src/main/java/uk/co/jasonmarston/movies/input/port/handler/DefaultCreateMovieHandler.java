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

@ApplicationScoped
public class DefaultCreateMovieHandler implements uk.co.jasonmarston.movies.input.port.CreateMovieHandler {
    private final MovieOutputPort movieOutputPort;
    private final ModelMapper modelMapper;

    @Inject
    public DefaultCreateMovieHandler(
            final MovieOutputPort movieOutputPort,
            @Validating
            final ModelMapper modelMapper
    ) {
        this.movieOutputPort = movieOutputPort;
        this.modelMapper = modelMapper;
    }

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
