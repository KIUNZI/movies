package uk.co.jasonmarston.movies.domain.validator;

/**
 * Utility interface providing static checks for domain invariants.
 *
 * <p>Invariants are business rules that must hold true throughout the lifetime of an
 * aggregate. Use {@link #requireNull(Object, String)} to assert that a field has not yet
 * been assigned a value when a one-time, immutable assignment is required.</p>
 *
 * @see Preconditions
 */
public interface Invariants {

    /**
     * Asserts that {@code object} is {@code null}, throwing an {@link IllegalStateException}
     * with the given {@code message} if it is not.
     *
     * <p>Typical use is to guard a field that must only be set once:</p>
     * <pre>{@code
     * Invariants.requireNull(this.publicId, "publicId must not change once set");
     * this.publicId = publicId;
     * }</pre>
     *
     * @param <T>     the type of the object being checked
     * @param object  the value to check; the assertion passes only when this is {@code null}
     * @param message the detail message for the exception if the assertion fails
     * @throws IllegalStateException if {@code object} is not {@code null}
     */
    static <T> void requireNull(final T object, final String message) {
        if (object != null) {
            throw new IllegalStateException(message);
        }
    }
}
