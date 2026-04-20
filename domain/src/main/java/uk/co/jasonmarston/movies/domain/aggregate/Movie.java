package uk.co.jasonmarston.movies.domain.aggregate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.jasonmarston.hexagonal.utility.InvariantValidation;
import uk.co.jasonmarston.movies.domain.arguments.CreateMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.UpdateMovieArgs;
import uk.co.jasonmarston.movies.domain.valueobject.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public final class  Movie {
    @NotNull
    @Valid
    @Setter(AccessLevel.NONE)
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
        this.publicId = PublicId.of(UUID.randomUUID());
    }

    public Movie(
            final PublicId publicId,
            final Long version,
            final Title title,
            final ReleaseDate release,
            final Director director
    ) {
        this();
        this.publicId = publicId;
        this.version = version;
        this.title = title;
        this.release = release;
        this.director = director;
        InvariantValidation.INSTANCE.validate(this);
    }

    public void update(final UpdateMovieArgs updateMovieArgs) {
        this.title = updateMovieArgs.title();
        this.release = updateMovieArgs.release();
        this.director = updateMovieArgs.director();
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) return true;
        if(!(o instanceof Movie other)) return false;
        if(this.publicId == null || other.publicId == null) return false;
        return this.publicId.equals(other.publicId);
    }

    @Override
    public int hashCode() {
        return (this.publicId != null)
            ? publicId.hashCode()
            : System.identityHashCode(this);
    }

    public static Movie create(final CreateMovieArgs createMovieArgs) {
        return new Movie(
                null,
                null,
                createMovieArgs.title(),
                createMovieArgs.release(),
                createMovieArgs.director()
        );
    }
}
