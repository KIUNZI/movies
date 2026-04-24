package uk.co.jasonmarston.movies.domain.validator;

import java.util.Objects;

/**
 * Utility interface providing static precondition checks for method arguments.
 *
 * <p>Preconditions are checked at the entry point of a method or constructor to ensure
 * that supplied arguments meet requirements before any work is performed.</p>
 *
 * @see Invariants
 */
public interface Preconditions {

    /**
     * Asserts that {@code object} is not {@code null}, throwing a
     * {@link NullPointerException} with the given {@code message} if it is.
     *
     * @param <T>     the type of the object being checked
     * @param object  the value to check; must not be {@code null}
     * @param message the detail message for the exception if the check fails
     * @throws NullPointerException if {@code object} is {@code null}
     */
    static <T> void requireNonNull(final T object, final String message) {
        Objects.requireNonNull(object, message);
    }
}
