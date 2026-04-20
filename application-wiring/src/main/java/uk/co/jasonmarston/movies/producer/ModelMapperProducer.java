package uk.co.jasonmarston.movies.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.record.RecordModule;
import uk.co.jasonmarston.movies.annotation.PersistenceAwareValidating;
import uk.co.jasonmarston.movies.annotation.Validating;
import uk.co.jasonmarston.movies.modelmapper.ValidatingModelMapper;

@ApplicationScoped
class ModelMapperProducer {
    private final Validator validator;

    @Inject
    public ModelMapperProducer(final Validator validator) {
        this.validator = validator;
    }

    @Produces
    @Validating
    @ApplicationScoped
    public ModelMapper validatingModelMapper() {
        final ModelMapper modelMapper = new ValidatingModelMapper(validator);

        modelMapper
            .registerModule(new RecordModule())
            .getConfiguration()
            .setFieldMatchingEnabled(false)
            .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE)
            .setPropertyCondition(Conditions.isNotNull());

        return modelMapper;
    }

    @Produces
    @PersistenceAwareValidating
    @ApplicationScoped
    public ModelMapper plainModelMapper() {
        final ModelMapper modelMapper = new ValidatingModelMapper(validator);

        modelMapper
                .registerModule(new RecordModule())
                .getConfiguration()
                .setFieldMatchingEnabled(false)
                .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setPropertyCondition(Conditions.isNotNull());

        return modelMapper;
    }
}