package uk.co.jasonmarston.movies.input.port;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.input.port.command.DeleteMovieCommand;

public interface DeleteMovieHandler {
    Uni<Void> handle(final DeleteMovieCommand deleteMovieCommand);
}
