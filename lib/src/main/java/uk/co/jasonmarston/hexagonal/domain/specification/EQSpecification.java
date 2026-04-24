package uk.co.jasonmarston.hexagonal.domain.specification;

/**
 * Specification that matches candidates equal to the provided value.
 *
 * @param <T> candidate type
 * @see NESpecification
 */
@SuppressWarnings("unused")
public class EQSpecification<T> extends AbstractSpecification<T> {
    private final T left;

    /**
     * Creates an equality specification.
     *
     * @param left the value used for equality comparison
     */
    public EQSpecification(final T left) {
        this.left = left;
    }
    /**
     * Evaluates whether {@code candidate} is equal to the configured left-hand value.
     *
     * @param candidate the value to evaluate
     * @return {@code true} when the candidate equals {@code left}; otherwise {@code false}
     */
    @Override
    public boolean isSatisfiedBy(final T candidate) {
        return left.equals(candidate);
    }
}
