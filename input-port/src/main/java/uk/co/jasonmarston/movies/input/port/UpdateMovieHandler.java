package uk.co.jasonmarston.movies.input.port;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.input.port.command.UpdateMovieCommand;

public interface UpdateMovieHandler {
    Uni<Movie> handle(final UpdateMovieCommand updateMovieCommand);
}
