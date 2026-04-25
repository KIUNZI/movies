package uk.co.jasonmarston.movies.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import uk.co.jasonmarston.kiunzi.utility.validator.InvariantValidation;

/**
 * Value object representing the title of a movie.
 *
 * <p>A {@code Title} wraps a non-blank string of at most 254 characters. Leading and
 * trailing whitespace is stripped on construction. Instances are created via the
 * {@link #of(String)} factory method, which validates all constraints before
 * returning the value object.</p>
 *
 * @see Director
 */
@EqualsAndHashCode
public class Title {
    @NotBlank
    @Size(max = 254)
    private final String value;

    private Title(final String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * Returns the trimmed title string.
     *
     * @return the title value; never {@code null} or blank after successful construction
     */
    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Returns the title as a plain string.
     *
     * @return the trimmed title value
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Factory method that creates and validates a {@code Title} from the given string.
     *
     * <p>Leading and trailing whitespace is stripped before validation.</p>
     *
     * @param value the raw title string
     * @return a validated {@code Title} instance
     * @throws uk.co.jasonmarston.kiunzi.utility.validator.DomainInvariantViolationException
     *         if {@code value} is blank or exceeds 254 characters
     */
    public static Title of(final String value) {
        final Title title = new Title(value);
        InvariantValidation.INSTANCE.validate(title);
        return title;
    }

    @JsonCreator
    static Title jsonCreator(final String value) {
        return Title.of(value);
    }
}
