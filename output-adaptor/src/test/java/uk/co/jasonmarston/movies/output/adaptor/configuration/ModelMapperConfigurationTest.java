package uk.co.jasonmarston.movies.output.adaptor.configuration;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;
import uk.co.jasonmarston.movies.output.adaptor.data.MovieData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelMapperConfigurationTest {

    @Test
    void initShouldSkipIdentityAndVersionFieldsForMovieDataToMovieDataMapping() {
        ModelMapper mapper = persistenceAwareMapper();
        new ModelMapperConfiguration(mapper).init();

        MovieData source = movieData(
            11L,
            PublicId.of(UUID.fromString("11111111-1111-1111-1111-111111111111")),
            9L,
            Title.of("Source Title"),
            ReleaseDate.of(LocalDate.of(2010, 1, 1)),
            Director.of("Source Director")
        );

        MovieData destination = movieData(
            22L,
            PublicId.of(UUID.fromString("22222222-2222-2222-2222-222222222222")),
            5L,
            Title.of("Destination Title"),
            ReleaseDate.of(LocalDate.of(2011, 2, 2)),
            Director.of("Destination Director")
        );

        mapper.map(source, destination);

        assertEquals(22L, destination.getId());
        assertEquals(PublicId.of(UUID.fromString("22222222-2222-2222-2222-222222222222")), destination.getPublicId());
        assertEquals(5L, destination.getVersion());
        assertEquals(Title.of("Source Title"), getField(destination, "title"));
        assertEquals(ReleaseDate.of(LocalDate.of(2010, 1, 1)), getField(destination, "release"));
        assertEquals(Director.of("Source Director"), getField(destination, "director"));
    }

    private static MovieData movieData(Long id, PublicId publicId, Long version, Title title, ReleaseDate release, Director director) {
        MovieData movieData = instantiateMovieData();
        setField(movieData, "id", id);
        setField(movieData, "publicId", publicId);
        setField(movieData, "version", version);
        setField(movieData, "title", title);
        setField(movieData, "release", release);
        setField(movieData, "director", director);
        return movieData;
    }

    private static ModelMapper persistenceAwareMapper() {
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
            throw new AssertionError("Failed to build persistence-aware mapper for tests", e);
        }
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
}


