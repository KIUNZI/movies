package uk.co.jasonmarston.hexagonal.domain.specification;
/**
 * Internal specification that composes two specifications using logical NOR.
 */

final class NorSpecification<T> extends AbstractSpecification<T> {
    private final Specification<T> left;
    private final Specification<T> right;
    /**
     * Creates a NOR-composed specification.
     *
     * @param left the left-hand specification
     * @param right the right-hand specification
     */

    public NorSpecification(final Specification<T> left, final Specification<T> right) {
        this.left = left;
        this.right = right;
    }
    /**
     * Evaluates the candidate using logical NOR semantics.
     *
     * @param candidate the value to evaluate
     * @return {@code true} when neither specification is satisfied
     */

    @Override
    public boolean isSatisfiedBy(final T candidate) {
        return !(left.isSatisfiedBy(candidate) || right.isSatisfiedBy(candidate));
    }
}
