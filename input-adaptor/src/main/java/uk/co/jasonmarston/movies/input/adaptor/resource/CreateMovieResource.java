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
import uk.co.jasonmarston.movies.domain.arguments.CreateMovieArgs;
import uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand;
import uk.co.jasonmarston.movies.input.port.CreateMovieHandler;

import java.net.URI;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@ApplicationScoped
@Path("/client-api")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Authenticated
//@RolesAllowed("user")
public class CreateMovieResource {
    private final ModelMapper modelMapper;
    private final CreateMovieHandler createMovieHandler;

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

    @POST
    @Path("/movie")
    public Uni<Response> createMovie(
            @Valid
            final CreateMovieResource createMovieResource
    ) {
        return Uni
            .createFrom()
            .item(createMovieResource)
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
