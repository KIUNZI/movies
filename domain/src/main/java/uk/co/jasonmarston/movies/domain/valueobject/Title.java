package uk.co.jasonmarston.movies.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import uk.co.jasonmarston.movies.domain.validator.InvariantValidation;

@EqualsAndHashCode
public class Title {
    @NotBlank
    @Size(max = 254)
    private final String value;

    private Title(final String value) {
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
