package uk.co.jasonmarston.hexagonal.domain.specification;

/**
 * Defines the Specification pattern contract for evaluating and composing predicates.
 *
 * <p>A specification encapsulates a single boolean rule for candidates of type
 * {@code T}. Specifications can be composed fluently via logical combinators to build
 * more complex business rules.</p>
 *
 * @param <T> candidate type evaluated by this specification
 * @see AbstractSpecification
 */
@SuppressWarnings("unused")
public sealed interface Specification<T> permits AbstractSpecification {
    /**
     * Evaluates whether the candidate satisfies this specification.
     *
     * @param candidate the value to evaluate
     * @return {@code true} when the candidate satisfies the rule; otherwise {@code false}
     */
    boolean isSatisfiedBy(final T candidate);

    /**
     * Combines this specification with another using logical AND.
     *
     * @param other the specification to combine with
     * @return a composed specification that is satisfied only when both specifications
     *         are satisfied
     */
    Specification<T> and(final Specification<T> other);

    /**
     * Combines this specification with another using logical OR.
     *
     * @param other the specification to combine with
     * @return a composed specification that is satisfied when either specification is
     *         satisfied
     */
    Specification<T> or(final Specification<T> other);

    /**
     * Combines this specification with another using logical XOR.
     *
     * @param other the specification to combine with
     * @return a composed specification that is satisfied when exactly one specification
     *         is satisfied
     */
    Specification<T> xor(final Specification<T> other);

    /**
     * Negates this specification.
     *
     * @return a specification that inverts the result of this specification
     */
    Specification<T> not();

    /**
     * Combines this specification with another using logical NAND.
     *
     * @param other the specification to combine with
     * @return a composed specification that is satisfied when not both specifications
     *         are satisfied
     */
    Specification<T> nand(final Specification<T> other);

    /**
     * Combines this specification with another using logical NOR.
     *
     * @param other the specification to combine with
     * @return a composed specification that is satisfied only when neither
     *         specification is satisfied
     */
    Specification<T> nor(final Specification<T> other);

    /**
     * Combines this specification with another using logical NXOR.
     *
     * @param other the specification to combine with
     * @return a composed specification that is satisfied when both specifications
     *         evaluate to the same boolean result
     */
    Specification<T> nxor(final Specification<T> other);
}
