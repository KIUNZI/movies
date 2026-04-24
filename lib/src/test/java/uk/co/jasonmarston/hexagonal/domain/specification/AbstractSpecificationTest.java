package uk.co.jasonmarston.hexagonal.domain.specification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractSpecificationTest {

    @Test
    void shouldComposeWithAllLogicalOperators() {
        Specification<Integer> even = new PredicateSpecification<>(candidate -> candidate % 2 == 0);
        Specification<Integer> positive = new PredicateSpecification<>(candidate -> candidate > 0);

        assertTrue(even.and(positive).isSatisfiedBy(2));
        assertFalse(even.and(positive).isSatisfiedBy(-2));

        assertTrue(even.or(positive).isSatisfiedBy(-2));
        assertFalse(even.or(positive).isSatisfiedBy(-3));

        assertTrue(even.xor(positive).isSatisfiedBy(-2));
        assertFalse(even.xor(positive).isSatisfiedBy(2));

        assertFalse(even.not().isSatisfiedBy(2));
        assertTrue(even.not().isSatisfiedBy(3));

        assertFalse(even.nand(positive).isSatisfiedBy(2));
        assertTrue(even.nand(positive).isSatisfiedBy(-2));

        assertFalse(even.nor(positive).isSatisfiedBy(-2));
        assertTrue(even.nor(positive).isSatisfiedBy(-3));

        assertTrue(even.nxor(positive).isSatisfiedBy(2));
        assertFalse(even.nxor(positive).isSatisfiedBy(-2));
    }

    @FunctionalInterface
    private interface IntPredicate {
        boolean test(int value);
    }

    private static final class PredicateSpecification<T extends Number> extends AbstractSpecification<T> {
        private final IntPredicate predicate;

        private PredicateSpecification(IntPredicate predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean isSatisfiedBy(T candidate) {
            return predicate.test(candidate.intValue());
        }
    }
}

