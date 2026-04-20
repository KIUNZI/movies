package uk.co.jasonmarston.movies.domain.arguments;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;

public record ReadMovieArgs(
        @NotNull
        @Valid
        PublicId publicId
) {
}
