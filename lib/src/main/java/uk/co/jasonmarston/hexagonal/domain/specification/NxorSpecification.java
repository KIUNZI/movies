package uk.co.jasonmarston.hexagonal.domain.specification;
/**
 * Internal specification that composes two specifications using logical NXOR.
 */

final class NxorSpecification<T> extends AbstractSpecification<T> {
    private final Specification<T> left;
    private final Specification<T> right;
    /**
     * Creates an NXOR-composed specification.
     *
     * @param left the left-hand specification
     * @param right the right-hand specification
     */

    public NxorSpecification(final Specification<T> left, final Specification<T> right) {
        this.left = left;
        this.right = right;
    }
    /**
     * Evaluates the candidate using logical NXOR semantics.
     *
     * @param candidate the value to evaluate
     * @return {@code true} when both specifications return the same result
     */

    @Override
    public boolean isSatisfiedBy(final T candidate) {
        return left.isSatisfiedBy(candidate) == right.isSatisfiedBy(candidate);
    }
}
