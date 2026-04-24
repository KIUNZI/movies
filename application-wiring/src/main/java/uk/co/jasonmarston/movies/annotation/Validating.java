package uk.co.jasonmarston.movies.annotation;

import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * CDI qualifier annotation that selects the validating {@link org.modelmapper.ModelMapper} bean.
 *
 * <p>Inject a {@code ModelMapper} qualified with {@code @Validating} to obtain a mapper
 * that automatically validates the mapped destination object using Bean Validation before
 * returning it. Any constraint violations cause a
 * {@link uk.co.jasonmarston.movies.domain.exception.DomainInvariantViolationException}
 * to be thrown.</p>
 *
 * <p>Applicable to fields, parameters, and methods.</p>
 *
 * @see PersistenceAwareValidating
 */
@Qualifier
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, METHOD })
public @interface Validating {
}
