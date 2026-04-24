package uk.co.jasonmarston.hexagonal.domain.specification;

/**
 * Specification that matches candidates not equal to the provided value.
 *
 * @param <T> candidate type
 * @see EQSpecification
 */
@SuppressWarnings("unused")
public class NESpecification<T> extends AbstractSpecification<T> {
    private final T left;

    /**
     * Creates a non-equality specification.
     *
     * @param left the value used for inequality comparison
     */
    public NESpecification(final T left) {
        this.left = left;
    }
    /**
     * Evaluates whether {@code candidate} is not equal to the configured left-hand value.
     *
     * @param candidate the value to evaluate
     * @return {@code true} when the candidate does not equal {@code left}; otherwise {@code false}
     */
    @Override
    public boolean isSatisfiedBy(final T candidate) {
        return !left.equals(candidate);
    }
}
