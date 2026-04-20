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
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class  MovieData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Valid
    @Column(name = "public_id", nullable = false, updatable = false)
    @Convert(converter = PublicIdConverter.class )
    private PublicId publicId;

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

    @Override
    public boolean equals(final Object o) {
        if(this == o) return true;
        if(!(o instanceof MovieData other)) return false;
        if(this.id == null || other.id == null) return false;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return (id != null)  ? id.hashCode() : System.identityHashCode(this);
    }
}
