package uk.co.jasonmarston.movies.input.adaptor.resource;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import uk.co.jasonmarston.kiunzi.utility.exception.mapper.response.ErrorResponse;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.CreateMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.UpdateMovieArgs;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;
import uk.co.jasonmarston.movies.input.adaptor.request.CreateMovieRequest;
import uk.co.jasonmarston.movies.input.adaptor.request.UpdateMovieRequest;
import uk.co.jasonmarston.movies.input.adaptor.response.MovieResponse;
import uk.co.jasonmarston.movies.input.port.CreateMovieHandler;
import uk.co.jasonmarston.movies.input.port.DeleteMovieHandler;
import uk.co.jasonmarston.movies.input.port.ReadMovieHandler;
import uk.co.jasonmarston.movies.input.port.UpdateMovieHandler;
import uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand;
import uk.co.jasonmarston.movies.input.port.command.DeleteMovieCommand;
import uk.co.jasonmarston.movies.input.port.command.ReadMovieCommand;
import uk.co.jasonmarston.movies.input.port.command.UpdateMovieCommand;
import uk.co.jasonmarston.movies.input.port.view.MovieView;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResourceTest {
    private static final PublicId PUBLIC_ID = PublicId.of(UUID.fromString("77a7b095-f1d2-4f43-a49f-00d41666d4f6"));
    private static final Title TITLE = Title.of("Arrival");
    private static final ReleaseDate RELEASE_DATE = ReleaseDate.of(LocalDate.of(2016, 9, 2));
    private static final Director DIRECTOR = Director.of("Denis Villeneuve");

    @Test
    void createMovieShouldReturnCreatedResponseAndLocation() {
        AtomicReference<CreateMovieCommand> captured = new AtomicReference<>();
        CreateMovieHandler handler = command -> {
            captured.set(command);
            return Uni.createFrom().item(PUBLIC_ID);
        };

        CreateMovieResource resource = new CreateMovieResource(validatingMapper(), handler);

        Response response = resource
            .createMovie(new CreateMovieRequest(TITLE, RELEASE_DATE, DIRECTOR))
            .await().indefinitely();

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("/client-api/movie/" + PUBLIC_ID, response.getLocation().toString());
        assertNotNull(captured.get());
        assertEquals(TITLE, captured.get().title());
        assertEquals(RELEASE_DATE, captured.get().release());
        assertEquals(DIRECTOR, captured.get().director());
    }

    @Test
    void readMovieShouldReturnBadRequestForInvalidUuid() {
        ReadMovieHandler handler = command -> Uni.createFrom().item((MovieView) null);
        ReadMovieResource resource = new ReadMovieResource(validatingMapper(), handler);

        Response response = resource.readMovie("not-a-uuid").await().indefinitely();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        ErrorResponse body = assertInstanceOf(ErrorResponse.class, response.getEntity());
        assertEquals("BAD_REQUEST_ERROR", body.code());
        assertEquals("Invalid UUID: not-a-uuid", body.message());
    }

    @Test
    void readMovieShouldReturnMovieResponseForValidUuid() {
        AtomicReference<ReadMovieCommand> captured = new AtomicReference<>();
        ReadMovieHandler handler = command -> {
            captured.set(command);
            return Uni.createFrom().item(new MovieView(PUBLIC_ID, 7L, TITLE, RELEASE_DATE, DIRECTOR));
        };

        ReadMovieResource resource = new ReadMovieResource(validatingMapper(), handler);

        Response response = resource.readMovie(PUBLIC_ID.toString()).await().indefinitely();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        MovieResponse body = assertInstanceOf(MovieResponse.class, response.getEntity());
        assertEquals(PUBLIC_ID, body.publicId());
        assertEquals(7L, body.version());
        assertEquals(TITLE, body.title());
        assertEquals(RELEASE_DATE, body.release());
        assertEquals(DIRECTOR, body.director());
        assertEquals(PUBLIC_ID, captured.get().publicId());
    }

    @Test
    void updateMovieShouldMapCommandAndReturnOkResponse() {
        AtomicReference<UpdateMovieCommand> captured = new AtomicReference<>();
        UpdateMovieHandler handler = command -> {
            captured.set(command);
            Movie movie = Movie.create(new CreateMovieArgs(TITLE, RELEASE_DATE, DIRECTOR));
            movie.update(new UpdateMovieArgs(command.title(), command.release(), command.director()));
            return Uni.createFrom().item(movie);
        };

        UpdateMovieResource resource = new UpdateMovieResource(validatingMapper(), handler);

        Response response = resource
            .updateMovie(new UpdateMovieRequest(PUBLIC_ID, 4L, TITLE, RELEASE_DATE, DIRECTOR))
            .await().indefinitely();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        MovieResponse body = assertInstanceOf(MovieResponse.class, response.getEntity());
        assertEquals(TITLE, body.title());
        assertEquals(RELEASE_DATE, body.release());
        assertEquals(DIRECTOR, body.director());
        assertEquals(PUBLIC_ID, captured.get().publicId());
        assertEquals(4L, captured.get().version());
    }

    @Test
    void deleteMovieShouldReturnNoContentForValidUuid() {
        AtomicReference<DeleteMovieCommand> captured = new AtomicReference<>();
        DeleteMovieHandler handler = command -> {
            captured.set(command);
            return Uni.createFrom().item((Void) null);
        };

        DeleteMovieResource resource = new DeleteMovieResource(handler);

        Response response = resource.deleteMovie(PUBLIC_ID.toString()).await().indefinitely();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals(PUBLIC_ID, captured.get().publicId());
    }

    @Test
    void deleteMovieShouldReturnBadRequestForInvalidUuid() {
        DeleteMovieHandler handler = command -> Uni.createFrom().item((Void) null);
        DeleteMovieResource resource = new DeleteMovieResource(handler);

        Response response = resource.deleteMovie("bad-uuid").await().indefinitely();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        ErrorResponse body = assertInstanceOf(ErrorResponse.class, response.getEntity());
        assertEquals("BAD_REQUEST_ERROR", body.code());
        assertEquals("Invalid UUID: bad-uuid", body.message());
    }

    private static ModelMapper validatingMapper() {
        try {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Class<?> mapperType = Class.forName("uk.co.jasonmarston.kiunzi.utility.producer.modelmapper.ValidatingModelMapper");
            ModelMapper mapper = (ModelMapper) mapperType
                .getConstructor(Validator.class)
                .newInstance(validator);

            mapper.getConfiguration()
                .setFieldMatchingEnabled(false)
                .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setPropertyCondition(Conditions.isNotNull());

            try {
                Class<?> moduleType = Class.forName("org.modelmapper.record.RecordModule");
                mapper.registerModule((org.modelmapper.Module) moduleType.getDeclaredConstructor().newInstance());
            } catch (ClassNotFoundException ignored) {
                // Record module is optional on this module's test classpath.
            }

            Class<?> configType = Class.forName("uk.co.jasonmarston.movies.input.adaptor.configuration.ModelMapperConfiguration");
            var ctor = configType.getDeclaredConstructor(ModelMapper.class);
            ctor.setAccessible(true);
            Object config = ctor.newInstance(mapper);
            var init = configType.getDeclaredMethod("init");
            init.setAccessible(true);
            init.invoke(config);
            return mapper;
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Failed to build validating mapper for tests", e);
        }
    }
}

