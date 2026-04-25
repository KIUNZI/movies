/**
 * Defines core movie domain models, value objects, commands, and domain exceptions.
 */
module domain {
    requires utility.invariant;
    requires utility.validator;
    requires utility.domain.exception;

    requires static lombok;

    requires jakarta.validation;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;

    exports uk.co.jasonmarston.movies.domain.aggregate;
    exports uk.co.jasonmarston.movies.domain.valueobject;
    exports uk.co.jasonmarston.movies.domain.arguments;
}