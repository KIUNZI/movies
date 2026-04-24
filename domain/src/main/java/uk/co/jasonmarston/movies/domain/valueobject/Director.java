package uk.co.jasonmarston.movies.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import uk.co.jasonmarston.movies.domain.validator.InvariantValidation;

/**
 * Value object representing the director of a movie.
 *
 * <p>A {@code Director} wraps a non-blank string of at most 254 characters. Leading and
 * trailing whitespace is stripped on construction. Instances are created via the
 * {@link #of(String)} factory method, which validates all constraints before
 * returning the value object.</p>
 *
 * @see Title
 */
@EqualsAndHashCode
public class Director {
    @NotBlank
    @Size(max = 254)
    private final String value;

    private Director(final String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * Returns the trimmed director name string.
     *
     * @return the director value; never {@code null} or blank after successful construction
     */
    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Returns the director name as a plain string.
     *
     * @return the trimmed director value
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Factory method that creates and validates a {@code Director} from the given string.
     *
     * <p>Leading and trailing whitespace is stripped before validation.</p>
     *
     * @param value the raw director name string
     * @return a validated {@code Director} instance
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if {@code value} is blank or exceeds 254 characters
     */
    public static Director of(final String value) {
        final Director director = new Director(value);
        InvariantValidation.INSTANCE.validate(director);
        return director;
    }

    @JsonCreator
    static Director jsonCreator(final String value) {
        return Director.of(value);
    }
}
