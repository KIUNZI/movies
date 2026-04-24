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

/**
 * CDI producer that creates and exposes {@link ModelMapper} instances as application-scoped beans.
 *
 * <p>Two qualified variants are produced:</p>
 * <ul>
 *   <li>{@link Validating} – a {@link ValidatingModelMapper} that validates mapped objects
 *       using Bean Validation before returning them.</li>
 *   <li>{@link PersistenceAwareValidating} – a {@link ValidatingModelMapper} configured
 *       for persistence-aware scenarios (null-safe property conditions).</li>
 * </ul>
 *
 * @see ValidatingModelMapper
 * @see Validating
 * @see PersistenceAwareValidating
 */
@ApplicationScoped
class ModelMapperProducer {
    private final Validator validator;

    /**
     * Constructs a {@code ModelMapperProducer} with the injected Bean Validation {@link Validator}.
     *
     * @param validator the Jakarta {@code Validator} passed to each produced {@link ModelMapper};
     *                  must not be {@code null}
     */
    @Inject
    public ModelMapperProducer(final Validator validator) {
        this.validator = validator;
    }

    /**
     * Produces a {@link Validating}-qualified {@link ModelMapper} that validates mapped
     * destination objects and supports Java records via {@link RecordModule}.
     *
     * <p>Configuration applied:</p>
     * <ul>
     *   <li>Field matching disabled (accessor-only mapping).</li>
     *   <li>Private method access allowed.</li>
     *   <li>Null source properties are skipped.</li>
     * </ul>
     *
     * @return a fully configured, application-scoped {@code ModelMapper}
     */
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

    /**
     * Produces a {@link PersistenceAwareValidating}-qualified {@link ModelMapper} for
     * persistence-aware mapping scenarios, supporting Java records via {@link RecordModule}.
     *
     * <p>Configuration applied:</p>
     * <ul>
     *   <li>Field matching disabled (accessor-only mapping).</li>
     *   <li>Private method access allowed.</li>
     *   <li>Null source properties are skipped.</li>
     * </ul>
     *
     * @return a fully configured, application-scoped {@code ModelMapper}
     */
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
