package uk.co.jasonmarston.movies.input.adaptor.resource;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.kiunzi.utility.producer.annotation.Validating;
import uk.co.jasonmarston.movies.input.adaptor.request.UpdateMovieRequest;
import uk.co.jasonmarston.movies.input.port.command.UpdateMovieCommand;
import uk.co.jasonmarston.movies.input.adaptor.response.MovieResponse;
import uk.co.jasonmarston.movies.input.port.UpdateMovieHandler;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * JAX-RS resource that updates an existing movie.
 *
 * <p>The resource accepts an update payload, maps it to an
 * {@link UpdateMovieCommand}, delegates to the {@link UpdateMovieHandler}, and maps
 * the updated aggregate to a {@link MovieResponse}.</p>
 *
 * @see UpdateMovieHandler
 * @see UpdateMovieCommand
 * @see MovieResponse
 */
@ApplicationScoped
@Path("/client-api")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Authenticated
//@RolesAllowed("user")
public class UpdateMovieResource {
    private final ModelMapper modelMapper;
    private final UpdateMovieHandler updateMovieHandler;

    /**
     * Constructs a resource that can map inbound update payloads and dispatch them
     * to the update-movie handler.
     *
     * @param modelMapper the validating mapper used to convert inbound and outbound
     *                    transport models
     * @param updateMovieHandler the handler that performs the update-movie use case
     */
    @Inject
    public UpdateMovieResource(
            @Validating
            final ModelMapper modelMapper,
            final UpdateMovieHandler updateMovieHandler
    )
    {
        this.modelMapper = modelMapper;
        this.updateMovieHandler = updateMovieHandler;
    }

    /**
     * Updates a movie using the supplied request payload.
     *
     * @param updateMovieRequest the inbound payload containing the target movie,
     *                            expected version, and replacement values
     * @return a {@link Uni} that emits the HTTP response containing the updated movie
     */
    @PUT
    @Path("/movie")
    public Uni<Response> updateMovie(
            @Valid
            final UpdateMovieRequest updateMovieRequest
    ) {
        return Uni
            .createFrom()
            .item(updateMovieRequest)
            .map(res -> modelMapper.map(res, UpdateMovieCommand.class))
            .flatMap(updateMovieHandler::handle)
            .map(movie -> modelMapper.map(movie, MovieResponse.class))
            .map(movieResponse -> Response
                .ok(movieResponse)
                .build()
            );
    }
}
