package uk.co.jasonmarston.movies.output.adaptor.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import uk.co.jasonmarston.movies.annotation.PersistenceAwareValidating;
import uk.co.jasonmarston.movies.output.adaptor.data.MovieData;

@ApplicationScoped
class ModelMapperConfiguration {
    private final ModelMapper modelMapper;

    @Inject
    public ModelMapperConfiguration(
            @PersistenceAwareValidating
            final ModelMapper modelMapper
    ) {
        this.modelMapper = modelMapper;
    }

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