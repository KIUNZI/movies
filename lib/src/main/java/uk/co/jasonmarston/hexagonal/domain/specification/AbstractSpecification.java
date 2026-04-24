package uk.co.jasonmarston.hexagonal.domain.specification;

/**
 * Base implementation that provides fluent logical combinators for specifications.
 *
 * @param <T> candidate type evaluated by this specification
 * @see Specification
 */
public non-sealed abstract class AbstractSpecification<T> implements Specification<T> {
    /**
     * Creates a base specification.
     */
    protected AbstractSpecification() {
    }

    /**
     * Combines this specification with {@code other} using logical AND.
     *
     * @param other the specification to combine with
     * @return an AND-composed specification
     */

    @Override
    public final Specification<T> and(final Specification<T> other) {
        return new AndSpecification<>(this, other);
    }

    /**
     * Combines this specification with {@code other} using logical OR.
     *
     * @param other the specification to combine with
     * @return an OR-composed specification
     */

    @Override
    public final Specification<T> or(final Specification<T> other) {
        return new OrSpecification<>(this, other);
    }

    /**
     * Combines this specification with {@code other} using logical XOR.
     *
     * @param other the specification to combine with
     * @return an XOR-composed specification
     */

    @Override
    public final Specification<T> xor(final Specification<T> other) {
        return new XorSpecification<>(this, other);
    }

    /**
     * Negates this specification.
     *
     * @return a negated specification
     */

    @Override
    public final Specification<T> not() {
        return new NotSpecification<>(this);
    }

    /**
     * Combines this specification with {@code other} using logical NAND.
     *
     * @param other the specification to combine with
     * @return a NAND-composed specification
     */

    @Override
    public final Specification<T> nand(final Specification<T> other) {
        return new NandSpecification<>(this, other);
    }

    /**
     * Combines this specification with {@code other} using logical NOR.
     *
     * @param other the specification to combine with
     * @return a NOR-composed specification
     */

    @Override
    public final Specification<T> nor(final Specification<T> other) {
        return new NorSpecification<>(this, other);
    }

    /**
     * Combines this specification with {@code other} using logical NXOR.
     *
     * @param other the specification to combine with
     * @return an NXOR-composed specification
     */

    @Override
    public final Specification<T> nxor(final Specification<T> other) {
        return new NxorSpecification<>(this, other);
    }
}
