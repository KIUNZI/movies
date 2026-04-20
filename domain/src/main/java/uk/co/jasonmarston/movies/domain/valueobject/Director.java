package uk.co.jasonmarston.movies.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import uk.co.jasonmarston.movies.domain.validator.InvariantValidation;

@EqualsAndHashCode
public class Director {
    @NotBlank
    @Size(max = 254)
    private final String value;

    private Director(final String value) {
        this.value = value == null ? null : value.trim();
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

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
