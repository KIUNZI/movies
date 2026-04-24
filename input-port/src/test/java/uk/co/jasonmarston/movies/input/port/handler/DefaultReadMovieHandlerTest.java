package uk.co.jasonmarston.movies.input.port.handler;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.CreateMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;
import uk.co.jasonmarston.movies.input.port.command.ReadMovieCommand;
import uk.co.jasonmarston.movies.input.port.view.MovieView;
import uk.co.jasonmarston.movies.output.port.MovieOutputPort;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultReadMovieHandlerTest {
    private static final Title TITLE = Title.of("Arrival");
    private static final ReleaseDate RELEASE = ReleaseDate.of(LocalDate.of(2016, 9, 2));
    private static final Director DIRECTOR = Director.of("Denis Villeneuve");

    @Test
    void handleShouldReadMovieAndMapItToMovieView() {
        Movie movie = Movie.create(new CreateMovieArgs(TITLE, RELEASE, DIRECTOR));
        AtomicReference<ReadMovieArgs> captured = new AtomicReference<>();

        MovieOutputPort outputPort = new MovieOutputPort() {
            @Override
            public Uni<Movie> createMovie(Movie movie) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }

            @Override
            public Uni<Movie> readMovie(ReadMovieArgs readMovieArgs) {
                captured.set(readMovieArgs);
                return Uni.createFrom().item(movie);
            }

            @Override
            public Uni<Movie> updateMovie(Movie movie) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }

            @Override
            public Uni<Void> deleteMovie(DeleteMovieArgs deleteMovieArgs) {
                return Uni.createFrom().failure(new UnsupportedOperationException());
            }
        };

        DefaultReadMovieHandler handler = new DefaultReadMovieHandler(outputPort, validatingMapper());
        ReadMovieCommand command = new ReadMovieCommand(movie.getPublicId());

        MovieView result = handler.handle(command).await().indefinitely();

        assertNotNull(captured.get());
        assertEquals(command.publicId(), captured.get().publicId());
        assertEquals(movie.getPublicId(), result.publicId());
        assertEquals(movie.getTitle(), result.title());
        assertEquals(movie.getRelease(), result.release());
        assertEquals(movie.getDirector(), result.director());
    }

    private static ModelMapper validatingMapper() {
        try {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Class<?> mapperType = Class.forName("uk.co.jasonmarston.movies.modelmapper.ValidatingModelMapper");
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

