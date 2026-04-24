package uk.co.jasonmarston.movies.output.adaptor.data;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;
import uk.co.jasonmarston.movies.output.adaptor.converter.DirectorConverter;
import uk.co.jasonmarston.movies.output.adaptor.converter.PublicIdConverter;
import uk.co.jasonmarston.movies.output.adaptor.converter.ReleaseDateConverter;
import uk.co.jasonmarston.movies.output.adaptor.converter.TitleConverter;

/**
 * Persistence entity representing a movie row in the {@code movies} table.
 *
 * <p>This data model stores the persistent representation of a domain movie aggregate,
 * including optimistic-locking version information and converted value-object fields.</p>
 *
 * @see uk.co.jasonmarston.movies.domain.aggregate.Movie
 */
@Entity
@Table(
        name = "movies",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_movies_public_id",
                columnNames = "public_id"
        ),
        indexes = @Index(
                name = "ix_movies_public_id",
                columnList = "public_id"
        )
)
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MovieData {
    /**
     * Returns the database surrogate identifier.
     *
     * @return the database identifier
     */
    @Getter(AccessLevel.PUBLIC)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * Returns the public business identifier.
     *
     * @return the public identifier
     */
    @Getter(AccessLevel.PUBLIC)
    @NotNull
    @Valid
    @Column(name = "public_id", nullable = false, updatable = false)
    @Convert(converter = PublicIdConverter.class)
    private PublicId publicId;

    /**
     * Returns the optimistic-locking version.
     *
     * @return the current version
     */
    @Getter(AccessLevel.PUBLIC)
    @Version
    private Long version;

    @NotNull
    @Valid
    @Column(name = "title", nullable = false, length = 254)
    @Convert(converter = TitleConverter.class)
    private Title title;

    @NotNull
    @Valid
    @Column(name = "releasedOn", nullable = false)
    @Convert(converter = ReleaseDateConverter.class)
    private ReleaseDate release;

    @NotNull
    @Valid
    @Column(name = "director", nullable = false, length = 254)
    @Convert(converter = DirectorConverter.class)
    private Director director;
}
