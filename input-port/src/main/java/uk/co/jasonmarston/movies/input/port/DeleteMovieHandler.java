package uk.co.jasonmarston.movies.input.port;

import io.smallrye.mutiny.Uni;
import uk.co.jasonmarston.movies.input.port.command.DeleteMovieCommand;

/**
 * Use-case boundary for deleting an existing movie.
 *
 * <p>Implementations translate a {@link DeleteMovieCommand} into delete arguments and
 * delegate the removal of the targeted movie to an output port.</p>
 *
 * @see DeleteMovieCommand
 */
public interface DeleteMovieHandler {

    /**
     * Deletes the movie described by the supplied command.
     *
     * @param deleteMovieCommand the validated command identifying the movie to delete
     * @return a {@link Uni} that completes when the delete operation finishes
     * @throws uk.co.jasonmarston.kiunzi.utility.domain.exception.NotFoundException
     *         if no movie exists for the supplied identifier
     */
    Uni<Void> handle(final DeleteMovieCommand deleteMovieCommand);
}
