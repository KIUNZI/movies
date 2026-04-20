package uk.co.jasonmarston.hexagonal.domain.specification;

public non-sealed abstract class AbstractSpecification<T> implements Specification<T> {
    @Override
    public final Specification<T> and(final Specification<T> other) {
        return new AndSpecification<>(this, other);
    }

    @Override
    public final Specification<T> or(final Specification<T> other) {
        return new OrSpecification<>(this, other);
    }

    @Override
    public final Specification<T> xor(final Specification<T> other) {
        return new XorSpecification<>(this, other);
    }

    @Override
    public final Specification<T> not() {
        return new NotSpecification<>(this);
    }

    @Override
    public final Specification<T> nand(final Specification<T> other) {
        return new NandSpecification<>(this, other);
    }

    @Override
    public final Specification<T> nor(final Specification<T> other) {
        return new NorSpecification<>(this, other);
    }

    @Override
    public final Specification<T> nxor(final Specification<T> other) {
        return new NxorSpecification<>(this, other);
    }
}
