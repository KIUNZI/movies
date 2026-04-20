package uk.co.jasonmarston.movies.input.port;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.input.port.command.ReadMovieCommand;
import uk.co.jasonmarston.movies.input.port.view.MovieView;

public interface ReadMovieHandler {
    Uni<MovieView> handle(final ReadMovieCommand readMovieCommand);
}
