package uk.co.jasonmarston.movies.domain.aggregate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uk.co.jasonmarston.hexagonal.utility.InvariantValidation;
import uk.co.jasonmarston.movies.domain.arguments.CreateMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.UpdateMovieArgs;
import uk.co.jasonmarston.movies.domain.validator.Invariants;
import uk.co.jasonmarston.movies.domain.validator.Preconditions;
import uk.co.jasonmarston.movies.domain.valueobject.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class  Movie {
    @NotNull
    @Valid
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private PublicId publicId;
    private Long version;
    @NotNull
    @Valid
    private Title title;
    @NotNull
    @Valid
    private ReleaseDate release;
    @NotNull
    @Valid
    private Director director;

    private Movie() {
    }

    private void setPublicId(final PublicId publicId) {
        Preconditions.requireNonNull(publicId, "publicId must not be null");
        Invariants.requireNull(this.publicId, "publicId must not change once set");
        this.publicId = publicId;
    }

    public void update(final UpdateMovieArgs updateMovieArgs) {
        Preconditions.requireNonNull(updateMovieArgs, "updateMovieArgs must not be null");
        this.title = updateMovieArgs.title();
        this.release = updateMovieArgs.release();
        this.director = updateMovieArgs.director();
    }

    public static Movie create(final CreateMovieArgs createMovieArgs) {
        Preconditions.requireNonNull(createMovieArgs, "createMovieArgs must not be null");
        final Movie movie = new Movie();
        movie.setPublicId(PublicId.of(UUID.randomUUID()));
        movie.setTitle(createMovieArgs.title());
        movie.setRelease(createMovieArgs.release());
        movie.setDirector(createMovieArgs.director());
        InvariantValidation.INSTANCE.validate(movie);
        return movie;
    }
}
