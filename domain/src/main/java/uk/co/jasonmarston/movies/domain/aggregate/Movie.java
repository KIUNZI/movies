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

/**
 * Aggregate root representing a movie in the domain.
 *
 * <p>A {@code Movie} encapsulates a movie's identity, title, release date, and director.
 * Instances are created exclusively through the {@link #create(CreateMovieArgs)} factory
 * method, which assigns a new {@link PublicId} and validates all domain invariants before
 * returning the new aggregate.</p>
 *
 * <p>Once a {@code Movie} is created its {@link PublicId} is immutable; any attempt to
 * change it after construction causes an {@link IllegalStateException}.</p>
 *
 * @see CreateMovieArgs
 * @see UpdateMovieArgs
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Movie {
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

    /**
     * Updates the mutable fields of this movie from the supplied arguments.
     *
     * @param updateMovieArgs the new title, release date and director to apply;
     *                        must not be {@code null}
     * @throws NullPointerException if {@code updateMovieArgs} is {@code null}
     */
    public void update(final UpdateMovieArgs updateMovieArgs) {
        Preconditions.requireNonNull(updateMovieArgs, "updateMovieArgs must not be null");
        this.title = updateMovieArgs.title();
        this.release = updateMovieArgs.release();
        this.director = updateMovieArgs.director();
    }

    /**
     * Factory method that creates and returns a new, fully validated {@code Movie}.
     *
     * <p>A random {@link PublicId} is assigned to the new aggregate. All domain
     * invariants are validated before the instance is returned.</p>
     *
     * @param createMovieArgs the title, release date and director for the new movie;
     *                        must not be {@code null}
     * @return a fully constructed and validated {@code Movie} instance
     * @throws NullPointerException if {@code createMovieArgs} is {@code null}
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if any domain invariant is violated
     */
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
