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

@ApplicationScoped
@Path("/client-api")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Authenticated
//@RolesAllowed("user")
public class ReadMovieResource {
    private final ModelMapper modelMapper;
    private final ReadMovieHandler readMovieHandler;

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
