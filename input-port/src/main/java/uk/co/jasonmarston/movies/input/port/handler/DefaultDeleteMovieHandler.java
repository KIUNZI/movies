package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.annotation.Validating;
import uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs;
import uk.co.jasonmarston.movies.input.port.DeleteMovieHandler;
import uk.co.jasonmarston.movies.input.port.command.DeleteMovieCommand;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

@ApplicationScoped
public class DefaultDeleteMovieHandler implements DeleteMovieHandler {
    private final MovieOutputPort movieOutputPort;
    private final ModelMapper modelMapper;

    @Inject
    public DefaultDeleteMovieHandler(
            final MovieOutputPort movieOutputPort,
            @Validating
            final ModelMapper modelMapper
    ) {
        this.movieOutputPort = movieOutputPort;
        this.modelMapper = modelMapper;
    }

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
