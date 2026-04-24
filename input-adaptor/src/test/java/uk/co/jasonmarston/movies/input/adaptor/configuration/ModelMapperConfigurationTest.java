package uk.co.jasonmarston.movies.input.adaptor.configuration;

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

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelMapperConfigurationTest {

    @Test
    void initShouldRegisterDomainToScalarConverters() {
        ModelMapper mapper = configuredMapper();

        assertEquals("Arrival", mapper.map(Title.of("Arrival"), String.class));
        assertEquals("Denis Villeneuve", mapper.map(Director.of("Denis Villeneuve"), String.class));

        UUID id = UUID.fromString("77a7b095-f1d2-4f43-a49f-00d41666d4f6");
        assertEquals(id, mapper.map(PublicId.of(id), UUID.class));

        LocalDate date = LocalDate.of(2016, 9, 2);
        assertEquals(date, mapper.map(ReleaseDate.of(date), LocalDate.class));
    }

    @Test
    void initShouldRegisterScalarToDomainConverters() {
        ModelMapper mapper = configuredMapper();

        Title title = mapper.map("Arrival", Title.class);
        Director director = mapper.map("Denis Villeneuve", Director.class);

        UUID id = UUID.fromString("77a7b095-f1d2-4f43-a49f-00d41666d4f6");
        PublicId publicId = mapper.map(id, PublicId.class);

        LocalDate date = LocalDate.of(2016, 9, 2);
        ReleaseDate releaseDate = mapper.map(date, ReleaseDate.class);

        assertEquals("Arrival", title.getValue());
        assertEquals("Denis Villeneuve", director.getValue());
        assertEquals(id, publicId.getValue());
        assertEquals(date, releaseDate.getValue());
    }

    private static ModelMapper configuredMapper() {
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

            new ModelMapperConfiguration(mapper).init();
            return mapper;
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Failed to build validating mapper for tests", e);
        }
    }
}

