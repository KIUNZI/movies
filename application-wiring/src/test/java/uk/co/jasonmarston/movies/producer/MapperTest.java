package uk.co.jasonmarston.movies.producer;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import uk.co.jasonmarston.movies.modelmapper.ValidatingModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class MapperTest {
    private ModelMapperProducer producer;

    @BeforeEach
    void setUp() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        producer = new ModelMapperProducer(validator);
    }

    @Test
    void validatingMapperShouldBeConfiguredForNullSafePropertyMapping() {
        ModelMapper mapper = producer.validatingModelMapper();

        assertInstanceOf(ValidatingModelMapper.class, mapper);
        assertConfiguration(mapper);

        Destination destination = new Destination("kept-value");
        mapper.map(new Source(null), destination);

        assertEquals("kept-value", destination.getTitle());
    }

    @Test
    void persistenceAwareMapperShouldSupportRecordMapping() {
        ModelMapper mapper = producer.plainModelMapper();

        assertInstanceOf(ValidatingModelMapper.class, mapper);
        assertConfiguration(mapper);

        TitleRecord destination = mapper.map(new Source("Dune"), TitleRecord.class);

        assertEquals("Dune", destination.title());
    }

    private static void assertConfiguration(ModelMapper mapper) {
        assertFalse(mapper.getConfiguration().isFieldMatchingEnabled());
        assertEquals(
            Configuration.AccessLevel.PRIVATE,
            mapper.getConfiguration().getMethodAccessLevel()
        );
    }

    private static final class Source {
        private final String title;

        private Source(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private static final class Destination {
        private String title;

        private Destination(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private record TitleRecord(String title) {
    }
}
