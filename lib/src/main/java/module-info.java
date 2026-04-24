/**
 * Provides reusable hexagonal architecture utilities shared across modules.
 *
 * <p>The module currently exposes invariant-validation helpers and specification
 * pattern primitives used by domain logic in dependent modules.</p>
 *
 * @see uk.co.jasonmarston.hexagonal.utility.InvariantValidation
 * @see uk.co.jasonmarston.hexagonal.domain.specification.Specification
 */
module lib {
    requires jakarta.validation;

    exports uk.co.jasonmarston.hexagonal.utility;
}