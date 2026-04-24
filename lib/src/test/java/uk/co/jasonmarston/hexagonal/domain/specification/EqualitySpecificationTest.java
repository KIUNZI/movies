package uk.co.jasonmarston.hexagonal.domain.specification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EqualitySpecificationTest {

    @Test
    void eqSpecificationShouldMatchOnlyEqualCandidate() {
        EQSpecification<String> specification = new EQSpecification<>("Arrival");

        assertTrue(specification.isSatisfiedBy("Arrival"));
        assertFalse(specification.isSatisfiedBy("Dune"));
    }

    @Test
    void neSpecificationShouldMatchOnlyNonEqualCandidate() {
        NESpecification<String> specification = new NESpecification<>("Arrival");

        assertFalse(specification.isSatisfiedBy("Arrival"));
        assertTrue(specification.isSatisfiedBy("Dune"));
    }

    @Test
    void equalitySpecificationsShouldThrowWhenLeftSideIsNull() {
        EQSpecification<String> eqSpecification = new EQSpecification<>(null);
        NESpecification<String> neSpecification = new NESpecification<>(null);

        assertThrows(NullPointerException.class, () -> eqSpecification.isSatisfiedBy("anything"));
        assertThrows(NullPointerException.class, () -> neSpecification.isSatisfiedBy("anything"));
    }
}

