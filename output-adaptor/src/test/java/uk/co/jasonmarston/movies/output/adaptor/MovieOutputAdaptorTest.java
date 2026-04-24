package uk.co.jasonmarston.movies.output.adaptor;

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
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;
import uk.co.jasonmarston.movies.output.adaptor.data.MovieData;
import uk.co.jasonmarston.movies.output.adaptor.repository.MovieRepository;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MovieOutputAdaptorTest {
    private static final PublicId PUBLIC_ID = PublicId.of(UUID.fromString("77a7b095-f1d2-4f43-a49f-00d41666d4f6"));
    private static final Title TITLE = Title.of("Arrival");
    private static final ReleaseDate RELEASE = ReleaseDate.of(LocalDate.of(2016, 9, 2));
    private static final Director DIRECTOR = Director.of("Denis Villeneuve");

    @Test
    void createMovieShouldMapAndDelegateToRepository() {
        AtomicReference<MovieData> captured = new AtomicReference<>();

        MovieRepository repository = new FakeRepository() {
            @Override
            public Uni<MovieData> createMovie(MovieData movieData) {
                captured.set(movieData);
                return Uni.createFrom().item(movieData);
            }
        };

        MovieOutputAdaptor adaptor = new MovieOutputAdaptor(persistenceAwareMapper(), repository);

        Movie created = adaptor.createMovie(sampleMovie()).await().indefinitely();

        assertEquals(PUBLIC_ID, captured.get().getPublicId());
        assertEquals(TITLE, created.getTitle());
        assertEquals(RELEASE, created.getRelease());
        assertEquals(DIRECTOR, created.getDirector());
        assertEquals(PUBLIC_ID, created.getPublicId());
    }

    @Test
    void readMovieShouldDelegateUsingPublicId() {
        AtomicReference<PublicId> captured = new AtomicReference<>();

        MovieRepository repository = new FakeRepository() {
            @Override
            public Uni<MovieData> readMovie(PublicId publicId) {
                captured.set(publicId);
                return Uni.createFrom().item(sampleMovieData());
            }
        };

        MovieOutputAdaptor adaptor = new MovieOutputAdaptor(persistenceAwareMapper(), repository);

        Movie movie = adaptor.readMovie(new ReadMovieArgs(PUBLIC_ID)).await().indefinitely();

        assertEquals(PUBLIC_ID, captured.get());
        assertEquals(PUBLIC_ID, movie.getPublicId());
        assertEquals(TITLE, movie.getTitle());
    }

    @Test
    void updateMovieShouldMapAndDelegateToRepository() {
        AtomicReference<MovieData> captured = new AtomicReference<>();

        MovieRepository repository = new FakeRepository() {
            @Override
            public Uni<MovieData> updateMovie(MovieData movieData) {
                captured.set(movieData);
                return Uni.createFrom().item(movieData);
            }
        };

        MovieOutputAdaptor adaptor = new MovieOutputAdaptor(persistenceAwareMapper(), repository);

        Movie updated = adaptor.updateMovie(sampleMovie()).await().indefinitely();

        assertEquals(PUBLIC_ID, captured.get().getPublicId());
        assertEquals(TITLE, updated.getTitle());
        assertEquals(PUBLIC_ID, updated.getPublicId());
    }

    @Test
    void deleteMovieShouldDelegateAndReturnVoid() {
        AtomicReference<PublicId> captured = new AtomicReference<>();

        MovieRepository repository = new FakeRepository() {
            @Override
            public Uni<Void> deleteMovie(PublicId publicId) {
                captured.set(publicId);
                return Uni.createFrom().voidItem();
            }
        };

        MovieOutputAdaptor adaptor = new MovieOutputAdaptor(persistenceAwareMapper(), repository);

        Void result = adaptor.deleteMovie(new DeleteMovieArgs(PUBLIC_ID)).await().indefinitely();

        assertNull(result);
        assertEquals(PUBLIC_ID, captured.get());
    }

    private static Movie sampleMovie() {
        Movie movie = instantiateMovie();
        setField(movie, "publicId", PUBLIC_ID);
        setField(movie, "version", 5L);
        setField(movie, "title", TITLE);
        setField(movie, "release", RELEASE);
        setField(movie, "director", DIRECTOR);
        return movie;
    }

    private static MovieData sampleMovieData() {
        MovieData movieData = instantiateMovieData();
        setField(movieData, "id", 42L);
        setField(movieData, "publicId", PUBLIC_ID);
        setField(movieData, "version", 5L);
        setField(movieData, "title", TITLE);
        setField(movieData, "release", RELEASE);
        setField(movieData, "director", DIRECTOR);
        return movieData;
    }

    private static MovieData instantiateMovieData() {
        try {
            Constructor<MovieData> ctor = MovieData.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Failed to instantiate MovieData", e);
        }
    }

    private static Movie instantiateMovie() {
        try {
            Constructor<Movie> ctor = Movie.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Failed to instantiate Movie", e);
        }
    }

    private static void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Failed setting field " + fieldName, e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T getField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(target);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Failed reading field " + fieldName, e);
        }
    }

    private static MovieData toMovieData(Movie source) {
        MovieData movieData = instantiateMovieData();
        setField(movieData, "id", 42L);
        setField(movieData, "publicId", source.getPublicId());
        setField(movieData, "version", source.getVersion());
        setField(movieData, "title", source.getTitle());
        setField(movieData, "release", source.getRelease());
        setField(movieData, "director", source.getDirector());
        return movieData;
    }

    private static Movie toMovie(MovieData source) {
        Movie movie = instantiateMovie();
        setField(movie, "publicId", source.getPublicId());
        setField(movie, "version", source.getVersion());
        setField(movie, "title", getField(source, "title"));
        setField(movie, "release", getField(source, "release"));
        setField(movie, "director", getField(source, "director"));
        return movie;
    }

    private static ModelMapper persistenceAwareMapper() {
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

            Class<?> configType = Class.forName("uk.co.jasonmarston.movies.output.adaptor.configuration.ModelMapperConfiguration");
            var ctor = configType.getDeclaredConstructor(ModelMapper.class);
            ctor.setAccessible(true);
            Object config = ctor.newInstance(mapper);
            var init = configType.getDeclaredMethod("init");
            init.setAccessible(true);
            init.invoke(config);

            mapper.createTypeMap(Movie.class, MovieData.class)
                .setConverter(ctx -> toMovieData(ctx.getSource()));
            mapper.createTypeMap(MovieData.class, Movie.class)
                .setConverter(ctx -> toMovie(ctx.getSource()));

            return mapper;
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Failed to build persistence-aware mapper for tests", e);
        }
    }

    private static class FakeRepository implements MovieRepository {
        @Override
        public Uni<MovieData> createMovie(MovieData movieData) {
            return Uni.createFrom().failure(new UnsupportedOperationException());
        }

        @Override
        public Uni<MovieData> readMovie(PublicId publicId) {
            return Uni.createFrom().failure(new UnsupportedOperationException());
        }

        @Override
        public Uni<MovieData> updateMovie(MovieData movieData) {
            return Uni.createFrom().failure(new UnsupportedOperationException());
        }

        @Override
        public Uni<Void> deleteMovie(PublicId publicId) {
            return Uni.createFrom().failure(new UnsupportedOperationException());
        }
    }
}



