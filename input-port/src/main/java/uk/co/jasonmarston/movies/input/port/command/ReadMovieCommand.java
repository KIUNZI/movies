package uk.co.jasonmarston.movies.input.port.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import uk.co.jasonmarston.movies.domain.valueobject.PublicId;

public record ReadMovieCommand(
        @NotNull
        @Valid
        PublicId publicId
) {
}
