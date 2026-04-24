package uk.co.jasonmarston.movies.annotation;

import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * CDI qualifier annotation that selects the persistence-aware validating
 * {@link org.modelmapper.ModelMapper} bean.
 *
 * <p>Inject a {@code ModelMapper} qualified with {@code @PersistenceAwareValidating} to
 * obtain a mapper intended for persistence-layer scenarios. Like {@link Validating}, it
 * validates the mapped destination via Bean Validation, but is a separate qualifier to
 * allow independent CDI configuration and interception.</p>
 *
 * <p>Applicable to fields, parameters, and methods.</p>
 *
 * @see Validating
 */
@Qualifier
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, METHOD })
public @interface PersistenceAwareValidating {
}

