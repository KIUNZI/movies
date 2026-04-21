package uk.co.jasonmarston.movies.domain.validator;

import java.util.Objects;

public interface Preconditions {
    static <T> void requireNonNull(final T object, final String message) {
        Objects.requireNonNull(object, message);
    }
}
