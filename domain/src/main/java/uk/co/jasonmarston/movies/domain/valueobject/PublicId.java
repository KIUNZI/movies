package uk.co.jasonmarston.movies.domain.valueobject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import uk.co.jasonmarston.movies.domain.validator.InvariantValidation;

import java.util.UUID;

@EqualsAndHashCode
public class PublicId {
    @NotNull
    private final UUID value;

    private PublicId(final UUID value) {
        this.value = value;
    }

    @JsonValue
    public UUID getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

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
