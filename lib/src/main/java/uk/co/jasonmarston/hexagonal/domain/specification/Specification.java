package uk.co.jasonmarston.hexagonal.domain.specification;

@SuppressWarnings("unused")
public sealed interface Specification<T> permits AbstractSpecification {
    boolean isSatisfiedBy(final T candidate);
    Specification<T> and(final Specification<T> other);
    Specification<T> or(final Specification<T> other);
    Specification<T> xor(final Specification<T> other);
    Specification<T> not();
    Specification<T> nand(final Specification<T> other);
    Specification<T> nor(final Specification<T> other);
    Specification<T> nxor(final Specification<T> other);
}
