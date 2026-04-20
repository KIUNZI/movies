package uk.co.jasonmarston.hexagonal.domain.specification;

final class NotSpecification<T> extends AbstractSpecification<T> {
    private final Specification<T> left;

    public NotSpecification(final Specification<T> left) {
        this.left = left;
    }

    @Override
    public boolean isSatisfiedBy(final T candidate) {
        return !left.isSatisfiedBy(candidate);
    }
}
