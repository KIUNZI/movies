package uk.co.jasonmarston.movies.input.adaptor.resource;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.input.adaptor.response.ErrorResponse;
import uk.co.jasonmarston.movies.input.port.DeleteMovieHandler;
import uk.co.jasonmarston.movies.input.port.command.DeleteMovieCommand;

import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
@Path("/client-api")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Authenticated
//@RolesAllowed("user")
public class DeleteMovieResource {
    private final DeleteMovieHandler deleteMovieHandler;

    @Inject
    public DeleteMovieResource(
            final DeleteMovieHandler deleteMovieHandler
    )
    {
        this.deleteMovieHandler = deleteMovieHandler;
    }

    @DELETE
    @Path("/movie/{publicId}")
    public Uni<Response> deleteMovie(
            @PathParam("publicId")
            final String publicId
    ) {
        return Uni.createFrom()
            .item(publicId)
            .map(UUID::fromString)
            .map(PublicId::of)
            .map(DeleteMovieCommand::new)
            .flatMap(deleteMovieHandler::handle)
            .map(ignored -> Response.noContent())
            .map(Response.ResponseBuilder::build)
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
