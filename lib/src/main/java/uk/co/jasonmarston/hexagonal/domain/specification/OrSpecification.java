package uk.co.jasonmarston.hexagonal.domain.specification;
/**
 * Internal specification that composes two specifications using logical OR.
 */

final class OrSpecification<T> extends AbstractSpecification<T> {
    private final Specification<T> left;
    private final Specification<T> right;
    /**
     * Creates an OR-composed specification.
     *
     * @param left the left-hand specification
     * @param right the right-hand specification
     */

    public OrSpecification(final Specification<T> left, final Specification<T> right) {
        this.left = left;
        this.right = right;
    }
    /**
     * Evaluates the candidate using logical OR semantics.
     *
     * @param candidate the value to evaluate
     * @return {@code true} when either specification is satisfied
     */

    @Override
    public boolean isSatisfiedBy(final T candidate) {
        return left.isSatisfiedBy(candidate) || right.isSatisfiedBy(candidate);
    }
}
