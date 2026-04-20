package uk.co.jasonmarston.movies.input.adaptor.response;

import java.util.List;

public record ValidationErrorResponse(
        String title,
        int status,
        List<Violation> violations
        ) {
    public record Violation(
            String field,
            String message
    ) {}
}