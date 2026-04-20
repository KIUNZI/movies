package uk.co.jasonmarston.movies.input.port;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand;

public interface CreateMovieHandler {
    Uni<PublicId> handle(final CreateMovieCommand createMovieCommand);
}
