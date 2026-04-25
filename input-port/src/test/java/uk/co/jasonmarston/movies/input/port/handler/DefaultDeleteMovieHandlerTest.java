package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.input.port.command.DeleteMovieCommand;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DefaultDeleteMovieHandlerTest {

    @Test
    void handleShouldMapCommandAndDelegateDelete() {
        PublicId publicId = PublicId.of(UUID.fromString("77a7b095-f1d2-4f43-a49f-00d41666d4f6"));
        AtomicReference<DeleteMovieArgs> captured = new AtomicReference<>();

        MovieOutputPort outputPort = new MovieOutputPort() {
            @Override
            public Uni<Movie> createMovie(Movie movie) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }

            @Override
            public Uni<Movie> readMovie(ReadMovieArgs readMovieArgs) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }

            @Override
            public Uni<Movie> updateMovie(Movie movie) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }

            @Override
            public Uni<Void> deleteMovie(DeleteMovieArgs deleteMovieArgs) {
                captured.set(deleteMovieArgs);
                return Uni.createFrom().item((Void) null);
            }
        };

        DefaultDeleteMovieHandler handler = new DefaultDeleteMovieHandler(outputPort, validatingMapper());

        Void result = handler.handle(new DeleteMovieCommand(publicId)).await().indefinitely();

        assertNull(result);
        assertNotNull(captured.get());
        assertEquals(publicId, captured.get().publicId());
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

            return mapper;
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Failed to build validating mapper for tests", e);
        }
    }
}


