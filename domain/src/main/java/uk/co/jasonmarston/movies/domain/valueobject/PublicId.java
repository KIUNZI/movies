package uk.co.jasonmarston.movies.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import uk.co.jasonmarston.movies.domain.validator.InvariantValidation;

import java.util.UUID;

/**
 * Value object representing the public identity of a movie.
 *
 * <p>A {@code PublicId} wraps a non-null {@link UUID} that uniquely identifies a
 * {@link uk.co.jasonmarston.movies.domain.aggregate.Movie} in the system.
 * Instances are created via the {@link #of(UUID)} factory method, which validates all
 * constraints before returning the value object.</p>
 */
@EqualsAndHashCode
public class PublicId {
    @NotNull
    private final UUID value;

    private PublicId(final UUID value) {
        this.value = value;
    }

    /**
     * Returns the underlying UUID.
     *
     * @return the UUID value; never {@code null} after successful construction
     */
    @JsonValue
    public UUID getValue() {
        return value;
    }

    /**
     * Returns the public id as a UUID string.
     *
     * @return the string representation of the underlying UUID
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Factory method that creates and validates a {@code PublicId} from the given UUID.
     *
     * @param value the UUID to wrap; must not be {@code null}
     * @return a validated {@code PublicId} instance
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if {@code value} is {@code null}
     */
    public static PublicId of(final UUID value) {
        final PublicId publicId = new PublicId(value);
        InvariantValidation.INSTANCE.validate(publicId);
        return publicId;
    }

    @JsonCreator
    static PublicId jsonCreator(final UUID value) {
        return PublicId.of(value);
    }
}
