package uk.co.jasonmarston.movies.modelmapper;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException;

import java.lang.reflect.Type;

/**
 * A {@link ModelMapper} extension that automatically validates the mapped destination
 * object using Bean Validation after every mapping operation.
 *
 * <p>If any constraint violations are found on the destination, a
 * {@link uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException}
 * is thrown instead of returning the partially-valid object.</p>
 *
 * @see ModelMapper
 * @see jakarta.validation.Validator
 */
public class ValidatingModelMapper extends ModelMapper {
    private final Validator validator;

    /**
     * Constructs a {@code ValidatingModelMapper} with the given Bean Validation {@link Validator}.
     *
     * @param validator the Jakarta {@code Validator} used to validate mapped destinations;
     *                  must not be {@code null}
     */
    public ValidatingModelMapper(Validator validator) {
        this.validator = validator;
    }

    /**
     * Maps properties from {@code source} to an existing {@code destination} object,
     * then validates the destination against all declared Bean Validation constraints.
     *
     * @param source      the source object to map from; must not be {@code null}
     * @param destination the destination object to map into; must not be {@code null}
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if the destination object fails validation after mapping
     */
    @Override
    public void map(Object source, Object destination) {
        super.map(source, destination);
        validateOrThrow(destination);
    }

    /**
     * Maps properties from {@code source} to a new instance of {@code destinationType},
     * then validates the result against all declared Bean Validation constraints.
     *
     * @param <T>             the destination type
     * @param source          the source object to map from; must not be {@code null}
     * @param destinationType the {@link Class} of the destination type; must not be {@code null}
     * @return the validated mapped destination instance
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if the destination object fails validation after mapping
     */
    @Override
    public <T> T map(Object source, Class<T> destinationType) {
        final T destination = super.map(source, destinationType);
        return validateOrThrow(destination);
    }

    /**
     * Maps properties from {@code source} to a new instance of the given generic
     * {@code destinationType}, then validates the result against all declared Bean
     * Validation constraints.
     *
     * @param <T>             the destination type
     * @param source          the source object to map from; must not be {@code null}
     * @param destinationType the {@link Type} of the destination; must not be {@code null}
     * @return the validated mapped destination instance
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if the destination object fails validation after mapping
     */
    @Override
    public <T> T map(Object source, Type destinationType) {
        final T destination = super.map(source, destinationType);
        return validateOrThrow(destination);
    }

    /**
     * Maps properties from {@code source} to an existing {@code destination} object
     * using the named type map, then validates the destination object.
     *
     * @param source      the source object to map from; must not be {@code null}
     * @param destination the destination object to map into; must not be {@code null}
     * @param typeMapName the name of the type map to use; must not be {@code null}
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if the destination object fails validation after mapping
     */
    @Override
    public void map(Object source, Object destination, String typeMapName) {
        super.map(source, destination, typeMapName);
        validateOrThrow(destination);
    }

    /**
     * Maps properties from {@code source} to a new instance of {@code destinationType}
     * using the named type map, then validates the result.
     *
     * @param <T>             the destination type
     * @param source          the source object to map from; must not be {@code null}
     * @param destinationType the {@link Class} of the destination type; must not be {@code null}
     * @param typeMapName     the name of the type map to use; must not be {@code null}
     * @return the validated mapped destination instance
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if the destination object fails validation after mapping
     */
    @Override
    public <T> T map(Object source, Class<T> destinationType, String typeMapName) {
        final T destination = super.map(source, destinationType, typeMapName);
        return validateOrThrow(destination);
    }

    /**
     * Maps properties from {@code source} to a new instance of the given generic
     * {@code destinationType} using the named type map, then validates the result.
     *
     * @param <T>             the destination type
     * @param source          the source object to map from; must not be {@code null}
     * @param destinationType the {@link Type} of the destination; must not be {@code null}
     * @param typeMapName     the name of the type map to use; must not be {@code null}
     * @return the validated mapped destination instance
     * @throws uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException
     *         if the destination object fails validation after mapping
     */
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
