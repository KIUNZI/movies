module domain {
    requires static lombok;
    requires jakarta.validation;
    requires lib;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;

    exports uk.co.jasonmarston.movies.domain.aggregate;
    exports uk.co.jasonmarston.movies.domain.exception;
    exports uk.co.jasonmarston.movies.domain.valueobject;
    exports uk.co.jasonmarston.movies.domain.arguments;
}