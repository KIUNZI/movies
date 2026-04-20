package uk.co.jasonmarston.hexagonal.domain.specification;

@SuppressWarnings("unused")
public class NESpecification<T> extends AbstractSpecification<T> {
    private final T left;
    public NESpecification(final T left) {
        this.left = left;
    }
    @Override
    public boolean isSatisfiedBy(final T candidate) {
        return !left.equals(candidate);
    }
}
