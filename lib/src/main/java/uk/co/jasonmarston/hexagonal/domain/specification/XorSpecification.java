package uk.co.jasonmarston.hexagonal.domain.specification;

final class XorSpecification<T> extends AbstractSpecification<T> {
    private final Specification<T> left;
    private final Specification<T> right;

    public XorSpecification(final Specification<T> left, final Specification<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(final T candidate) {
        return left.isSatisfiedBy(candidate) ^ right.isSatisfiedBy(candidate);
    }
}
