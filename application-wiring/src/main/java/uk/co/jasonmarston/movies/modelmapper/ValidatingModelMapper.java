package uk.co.jasonmarston.movies.modelmapper;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;

import java.lang.reflect.Type;

public class ValidatingModelMapper extends ModelMapper {
    private final Validator validator;

    public ValidatingModelMapper(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void map(Object source, Object destination) {
        super.map(source, destination);
        validateOrThrow(destination);
    }

    @Override
    public <T> T map(Object source, Class<T> destinationType) {
        final T destination = super.map(source, destinationType);
        return validateOrThrow(destination);
    }

    @Override
    public <T> T map(Object source, Type destinationType) {
        final T destination = super.map(source, destinationType);
        return validateOrThrow(destination);
    }

    @Override
    public void map(Object source, Object destination, String typeMapName) {
        super.map(source, destination, typeMapName);
        validateOrThrow(destination);
    }

    @Override
    public <T> T map(Object source, Class<T> destinationType, String typeMapName) {
        final T destination = super.map(source, destinationType, typeMapName);
        return validateOrThrow(destination);
    }

    @Override
    public <T> T map(Object source, Type destinationType, String typeMapName) {
        final T destination = super.map(source, destinationType, typeMapName);
        return validateOrThrow(destination);
    }

    private <T> T validateOrThrow(T obj) {
        var violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            throw new DomainInvariantViolationException(violations);
        }
        return obj;
    }
}
