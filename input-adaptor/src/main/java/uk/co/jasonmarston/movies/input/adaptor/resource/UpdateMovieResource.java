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
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.input.port.command.UpdateMovieCommand;
import uk.co.jasonmarston.movies.input.adaptor.response.MovieResponse;
import uk.co.jasonmarston.movies.input.port.UpdateMovieHandler;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@ApplicationScoped
@Path("/client-api")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Authenticated
//@RolesAllowed("user")
public class UpdateMovieResource {
    private final ModelMapper modelMapper;
    private final UpdateMovieHandler updateMovieHandler;

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

    @PUT
    @Path("/movie")
    public Uni<Response> updateMovie(
            @Valid
            final UpdateMovieCommand updateMovieResource
    ) {
        return Uni
            .createFrom()
            .item(updateMovieResource)
            .map(res -> modelMapper.map(res, UpdateMovieCommand.class))
            .flatMap(updateMovieHandler::handle)
            .map(movie -> modelMapper.map(movie, MovieResponse.class))
            .map(movieResponse -> Response
                .ok(movieResponse)
                .build()
            );
    }
}
