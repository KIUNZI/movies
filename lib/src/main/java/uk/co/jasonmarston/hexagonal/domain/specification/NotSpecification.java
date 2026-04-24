package uk.co.jasonmarston.hexagonal.domain.specification;
/**
 * Internal specification that negates another specification.
 */

final class NotSpecification<T> extends AbstractSpecification<T> {
    private final Specification<T> left;
    /**
     * Creates a NOT-composed specification.
     *
     * @param left the specification to negate
     */

    public NotSpecification(final Specification<T> left) {
        this.left = left;
    }
    /**
     * Evaluates the candidate by negating the wrapped specification result.
     *
     * @param candidate the value to evaluate
     * @return {@code true} when the wrapped specification is not satisfied
     */

    @Override
    public boolean isSatisfiedBy(final T candidate) {
        return !left.isSatisfiedBy(candidate);
    }
}
