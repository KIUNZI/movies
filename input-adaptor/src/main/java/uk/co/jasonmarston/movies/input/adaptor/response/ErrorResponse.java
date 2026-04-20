package uk.co.jasonmarston.movies.input.adaptor.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ErrorResponse(
        String code,
        String message
) {
}
