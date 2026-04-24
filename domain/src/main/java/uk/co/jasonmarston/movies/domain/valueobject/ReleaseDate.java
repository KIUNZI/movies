package uk.co.jasonmarston.movies.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.EqualsAndHashCode;
import uk.co.jasonmarston.movies.domain.validator.InvariantValidation;

import java.time.LocalDate;

/**
 * Value object representing the release date of a movie.
 *
 * <p>A {@code ReleaseDate} wraps a non-null {@link LocalDate} that must be in the past.
 * Instances are created via the {@link #of(LocalDate)} factory method, which validates
 * all constraints before returning the value object.</p>
 */
@EqualsAndHashCode
public class ReleaseDate {
    @NotNull
    @Past
    private final LocalDate value;

    private ReleaseDate(final LocalDate value) {
        this.value = value;
    }

    /**
     * Returns the release date.
     *
     * @return the {@link LocalDate} value; never {@code null} after successful construction
     */
    @JsonValue
    public LocalDate getValue() {
        return value;
    }

    /**
     * Returns the release date as an ISO-8601 string.
     *
     * @return the ISO-8601 representation of the release date
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Factory method that creates and validates a {@code ReleaseDate} from the given date.
     *
     * @param value the release date; must not be {@code null} and must be in the past
     * @return a validated {@code ReleaseDate} instance
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if {@code value} is {@code null} or is not in the past
     */
    public static ReleaseDate of(final LocalDate value) {
        final ReleaseDate releaseDate = new ReleaseDate(value);
        InvariantValidation.INSTANCE.validate(releaseDate);
        return releaseDate;
    }

    @JsonCreator
    static ReleaseDate jsonCreator(final LocalDate value) {
        return ReleaseDate.of(value);
    }
}
