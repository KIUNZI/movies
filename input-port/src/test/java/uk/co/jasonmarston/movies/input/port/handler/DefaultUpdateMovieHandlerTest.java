package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import uk.co.jasonmarston.kiunzi.utility.domain.exception.VersionMismatchException;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.CreateMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;
import uk.co.jasonmarston.movies.input.port.command.UpdateMovieCommand;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultUpdateMovieHandlerTest {
    private static final Title ORIGINAL_TITLE = Title.of("Arrival");
    private static final ReleaseDate ORIGINAL_RELEASE = ReleaseDate.of(LocalDate.of(2016, 9, 2));
    private static final Director ORIGINAL_DIRECTOR = Director.of("Denis Villeneuve");

    private static final Title UPDATED_TITLE = Title.of("Blade Runner 2049");
    private static final ReleaseDate UPDATED_RELEASE = ReleaseDate.of(LocalDate.of(2017, 10, 6));
    private static final Director UPDATED_DIRECTOR = Director.of("Denis Villeneuve");

    @Test
    void handleShouldUpdateMovieWhenVersionMatches() {
        Movie movie = Movie.create(new CreateMovieArgs(ORIGINAL_TITLE, ORIGINAL_RELEASE, ORIGINAL_DIRECTOR));
        setVersion(movie, 5L);

        MovieOutputPort outputPort = new MovieOutputPort() {
            @Override
            public Uni<Movie> createMovie(Movie movie) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }

            @Override
            public Uni<Movie> readMovie(ReadMovieArgs readMovieArgs) {
                return Uni.createFrom().item(movie);
            }

            @Override
            public Uni<Movie> updateMovie(Movie updatedMovie) {
                return Uni.createFrom().item(updatedMovie);
            }

            @Override
            public Uni<Void> deleteMovie(DeleteMovieArgs deleteMovieArgs) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }
        };

        DefaultUpdateMovieHandler handler = new DefaultUpdateMovieHandler(outputPort, validatingMapper());
        UpdateMovieCommand command = new UpdateMovieCommand(
            movie.getPublicId(),
            5L,
            UPDATED_TITLE,
            UPDATED_RELEASE,
            UPDATED_DIRECTOR
        );

        Movie result = handler.handle(command).await().indefinitely();

        assertEquals(UPDATED_TITLE, result.getTitle());
        assertEquals(UPDATED_RELEASE, result.getRelease());
        assertEquals(UPDATED_DIRECTOR, result.getDirector());
        assertEquals(5L, result.getVersion());
    }

    @Test
    void handleShouldThrowWhenVersionMismatchAndSkipUpdate() {
        Movie movie = Movie.create(new CreateMovieArgs(ORIGINAL_TITLE, ORIGINAL_RELEASE, ORIGINAL_DIRECTOR));
        setVersion(movie, 3L);
        AtomicBoolean updateCalled = new AtomicBoolean(false);

        MovieOutputPort outputPort = new MovieOutputPort() {
            @Override
            public Uni<Movie> createMovie(Movie movie) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }

            @Override
            public Uni<Movie> readMovie(ReadMovieArgs readMovieArgs) {
                return Uni.createFrom().item(movie);
            }

            @Override
            public Uni<Movie> updateMovie(Movie movie) {
                updateCalled.set(true);
                return Uni.createFrom().item(movie);
            }

            @Override
            public Uni<Void> deleteMovie(DeleteMovieArgs deleteMovieArgs) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }
        };

        DefaultUpdateMovieHandler handler = new DefaultUpdateMovieHandler(outputPort, validatingMapper());
        UpdateMovieCommand command = new UpdateMovieCommand(
            movie.getPublicId(),
            4L,
            UPDATED_TITLE,
            UPDATED_RELEASE,
            UPDATED_DIRECTOR
        );

        VersionMismatchException ex = assertThrows(
            VersionMismatchException.class,
            () -> handler.handle(command).await().indefinitely()
        );

        assertEquals("Movie was modified by another request", ex.getMessage());
        assertFalse(updateCalled.get());
    }

    private static void setVersion(Movie movie, Long version) {
        try {
            Field versionField = Movie.class.getDeclaredField("version");
            versionField.setAccessible(true);
            versionField.set(movie, version);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Unable to set version for test setup", e);
        }
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


