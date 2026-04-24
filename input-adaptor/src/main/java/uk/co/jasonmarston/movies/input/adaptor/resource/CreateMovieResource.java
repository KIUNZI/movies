package uk.co.jasonmarston.movies.input.adaptor.resource;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.annotation.Validating;
import uk.co.jasonmarston.movies.input.adaptor.request.CreateMovieRequest;
import uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand;
import uk.co.jasonmarston.movies.input.port.CreateMovieHandler;

import java.net.URI;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * JAX-RS resource that handles requests to create new movies.
 *
 * <p>The resource validates the incoming payload, maps it to a
 * {@link uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand}, and
 * delegates execution to the {@link CreateMovieHandler} use-case boundary.</p>
 *
 * @see CreateMovieHandler
 * @see uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand
 */
@ApplicationScoped
@Path("/client-api")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Authenticated
//@RolesAllowed("user")
public class CreateMovieResource {
    private final ModelMapper modelMapper;
    private final CreateMovieHandler createMovieHandler;

    /**
     * Constructs a resource that can map inbound create requests and dispatch them
     * to the create-movie handler.
     *
     * @param modelMapper the validating mapper used to convert inbound transport models
     *                    into input-port command objects
     * @param createMovieHandler the handler that performs the create-movie use case
     */
    @Inject
    public CreateMovieResource(
            @Validating
            final ModelMapper modelMapper,
            final  CreateMovieHandler createMovieHandler
    )
    {
        this.modelMapper = modelMapper;
        this.createMovieHandler = createMovieHandler;
    }

    /**
     * Creates a movie from the supplied request payload.
     *
     * <p>On success the returned response has HTTP status {@code 201 Created} and a
     * {@code Location} header pointing at the newly created movie resource.</p>
     *
     * @param createMovieRequest the inbound payload describing the movie to create
     * @return a {@link Uni} that emits the HTTP response for the create operation
     */
    @POST
    @Path("/movie")
    public Uni<Response> createMovie(
            @Valid
            final CreateMovieRequest createMovieRequest
    ) {
        return Uni
            .createFrom()
            .item(createMovieRequest)
            .map(res -> modelMapper.map(res, CreateMovieCommand.class))
            .flatMap(createMovieHandler::handle)
            .map(publicId -> Response
                .created(URI
                    .create("/client-api/movie/" + publicId)
                )
                .build()
            );
    }
}
