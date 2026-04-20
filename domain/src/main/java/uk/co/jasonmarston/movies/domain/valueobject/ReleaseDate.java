package uk.co.jasonmarston.movies.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.EqualsAndHashCode;
import uk.co.jasonmarston.movies.domain.validator.InvariantValidation;

import java.time.LocalDate;

@EqualsAndHashCode
public class ReleaseDate {
    @NotNull
    @Past
    private final LocalDate value;

    private ReleaseDate(final LocalDate value) {
        this.value = value;
    }

    @JsonValue
    public LocalDate getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

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
