package uk.co.jasonmarston.movies.domain.validator;

public interface Invariants {
    static <T> void requireNull(final T object, final String message) {
        if(object != null) {
            throw new IllegalStateException(message);
        }
    }
}
