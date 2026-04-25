package uk.co.jasonmarston.movies.input.adaptor.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.kiunzi.utility.producer.annotation.Validating;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Internal CDI configuration that registers transport-to-domain and domain-to-
 * transport converters for the input-adaptor {@link ModelMapper}.
 *
 * <p>The configured converters allow REST payloads and responses to work with scalar
 * transport representations while preserving the domain layer's strongly typed value
 * objects.</p>
 *
 * @see Director
 * @see Title
 * @see PublicId
 * @see ReleaseDate
 */
@ApplicationScoped
class ModelMapperConfiguration {
    private final ModelMapper modelMapper;

    /**
     * Constructs the configuration for the validating {@link ModelMapper} used by the
     * input-adaptor module.
     *
     * @param modelMapper the validating mapper instance to customize with converters
     */
    @Inject
    public ModelMapperConfiguration(
            @Validating
            final ModelMapper modelMapper
    ) {
        this.modelMapper = modelMapper;
    }

    /**
     * Registers the converters needed to map between domain value objects and their
     * JSON-friendly scalar representations.
     */
    @PostConstruct
    public void init() {
        modelMapper
           .addConverter(ctx ->
                ctx.getSource() == null
                    ? null
                    : ctx.getSource().getValue(),
                Director.class,
                String.class
            );
        modelMapper
           .addConverter(ctx ->
                        ctx.getSource() == null
                            ? null
                            : ctx.getSource().getValue(),
                Title.class,
                String.class
            );
        modelMapper
            .addConverter(ctx ->
                ctx.getSource() == null
                    ? null
                    : ctx.getSource().getValue(),
                PublicId.class,
                UUID.class
            );
        modelMapper
            .addConverter(ctx ->
                ctx.getSource() == null
                    ? null
                    : ctx.getSource().getValue(),
                ReleaseDate.class,
                LocalDate.class
            );
        modelMapper
            .addConverter(ctx ->
                ctx.getSource() == null
                    ? null
                    : Director.of(ctx.getSource()),
                String.class,
                Director.class
            );
        modelMapper
            .addConverter(ctx ->
                ctx.getSource() == null
                    ? null
                    : Title.of(ctx.getSource()),
                String.class,
                Title.class
            );
        modelMapper
            .addConverter(ctx ->
                ctx.getSource() == null
                    ? null
                    : PublicId.of(ctx.getSource()),
                UUID.class,
                PublicId.class
            );
        modelMapper
            .addConverter(ctx ->
                ctx.getSource() == null
                    ? null
                    : ReleaseDate.of(ctx.getSource()),
                LocalDate.class,
                ReleaseDate.class
            );
    }
}
