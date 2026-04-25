package uk.co.jasonmarston.movies.output.adaptor.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import uk.co.jasonmarston.kiunzi.utility.producer.annotation.PersistenceAwareValidating;
import uk.co.jasonmarston.movies.output.adaptor.data.MovieData;
/**
 * Internal configuration that customizes ModelMapper behavior for persistence updates.
 *
 * <p>The configuration prevents identifier and version fields from being overwritten
 * during {@link MovieData} to {@link MovieData} mapping operations.</p>
 */

@ApplicationScoped
class ModelMapperConfiguration {
    private final ModelMapper modelMapper;

    /**
     * Constructs the model-mapper configuration component.
     *
     * @param modelMapper the persistence-aware mapper instance to customize
     */
    @Inject
    public ModelMapperConfiguration(
            @PersistenceAwareValidating
            final ModelMapper modelMapper
    ) {
        this.modelMapper = modelMapper;
    }

    /**
     * Registers mapping rules for persistence-safe entity updates.
     */
    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<MovieData, MovieData>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getPublicId());
                skip(destination.getVersion());
            }
        });
    }
}
