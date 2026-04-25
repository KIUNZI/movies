package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;
import uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultCreateMovieHandlerTest {
    private static final Title TITLE = Title.of("Arrival");
    private static final ReleaseDate RELEASE = ReleaseDate.of(LocalDate.of(2016, 9, 2));
    private static final Director DIRECTOR = Director.of("Denis Villeneuve");

    @Test
    void handleShouldCreateAndPersistMovieThenReturnPublicId() {
        AtomicReference<Movie> createdMovie = new AtomicReference<>();

        MovieOutputPort outputPort = new MovieOutputPort() {
            @Override
            public Uni<Movie> createMovie(Movie movie) {
                createdMovie.set(movie);
                return Uni.createFrom().item(movie);
            }

            @Override
            public Uni<Movie> readMovie(uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs readMovieArgs) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }

            @Override
            public Uni<Movie> updateMovie(Movie movie) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }

            @Override
            public Uni<Void> deleteMovie(uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs deleteMovieArgs) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }
        };

        DefaultCreateMovieHandler handler = new DefaultCreateMovieHandler(outputPort, validatingMapper());

        PublicId publicId = handler
            .handle(new CreateMovieCommand(TITLE, RELEASE, DIRECTOR))
            .await().indefinitely();

        assertNotNull(createdMovie.get());
        assertEquals(TITLE, createdMovie.get().getTitle());
        assertEquals(RELEASE, createdMovie.get().getRelease());
        assertEquals(DIRECTOR, createdMovie.get().getDirector());
        assertNotNull(createdMovie.get().getPublicId());
        assertEquals(createdMovie.get().getPublicId(), publicId);
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


