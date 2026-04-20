package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.annotation.Validating;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;
import uk.co.jasonmarston.movies.input.port.command.ReadMovieCommand;
import uk.co.jasonmarston.movies.input.port.view.MovieView;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

import java.util.Optional;

@ApplicationScoped
public class DefaultReadMovieHandler implements uk.co.jasonmarston.movies.input.port.ReadMovieHandler {
    private final MovieOutputPort movieOutputPort;
    private final ModelMapper modelMapper;

    @Inject
    public DefaultReadMovieHandler(
            final MovieOutputPort movieOutputPort,
            @Validating
            final ModelMapper modelMapper
    ) {
        this.movieOutputPort = movieOutputPort;
        this.modelMapper = modelMapper;
    }

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
