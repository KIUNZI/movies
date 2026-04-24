package uk.co.jasonmarston.movies.input.adaptor.resource;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.annotation.Validating;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;
import uk.co.jasonmarston.movies.input.adaptor.response.MovieResponse;
import uk.co.jasonmarston.movies.input.port.ReadMovieHandler;
import uk.co.jasonmarston.movies.input.port.command.ReadMovieCommand;

import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * JAX-RS resource that retrieves a movie by its public identifier.
 *
 * <p>The resource converts the path parameter into a
 * {@link uk.co.jasonmarston.movies.input.port.command.ReadMovieCommand}, delegates
 * to the {@link ReadMovieHandler}, and maps the resulting view model to a
 * {@link MovieResponse}.</p>
 *
 * @see ReadMovieHandler
 * @see uk.co.jasonmarston.movies.input.port.command.ReadMovieCommand
 * @see MovieResponse
 */
@ApplicationScoped
@Path("/client-api")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Authenticated
//@RolesAllowed("user")
public class ReadMovieResource {
    private final ModelMapper modelMapper;
    private final ReadMovieHandler readMovieHandler;

    /**
     * Constructs a resource that can map movie lookups to the read-movie handler.
     *
     * @param modelMapper the validating mapper used to convert handler results into
     *                    response payloads
     * @param readMovieHandler the handler that performs the read-movie use case
     */
    @Inject
    public ReadMovieResource(
            @Validating
            final ModelMapper modelMapper,
            final ReadMovieHandler readMovieHandler
    )
    {
        this.modelMapper = modelMapper;
        this.readMovieHandler = readMovieHandler;
    }

    /**
     * Retrieves the movie identified by the supplied public identifier.
     *
     * <p>If the path parameter is not a valid UUID string, the returned response uses
     * HTTP status {@code 400 Bad Request} with an {@link ErrorResponse} body.</p>
     *
     * @param publicId the public identifier path parameter supplied by the client
     * @return a {@link Uni} that emits the HTTP response containing the movie or an
     *         error payload
     */
    @GET
    @Path("/movie/{publicId}")
    public Uni<Response> readMovie(
            @PathParam("publicId")
            final String publicId
    ) {
        return Uni.createFrom()
            .item(publicId)
            .map(UUID::fromString)
            .map(PublicId::of)
            .map(ReadMovieCommand::new)
            .flatMap(readMovieHandler::handle)
            .map(movie -> Response
                .ok(modelMapper.map(movie, MovieResponse.class))
                .build()
            )
            .onFailure(IllegalArgumentException.class)
            .recoverWithItem(() -> Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(
                    "BAD_REQUEST_ERROR",
                    "Invalid UUID: " + publicId
                ))
                .build()
            );
    }
}
