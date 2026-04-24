/**
 * Provides shared annotations and wiring dependencies used across adapters and ports.
 */
module application.wiring {
    requires jakarta.cdi;
    requires jakarta.validation;
    requires org.modelmapper;
    requires org.modelmapper.module.record;
    requires transitive domain;

    exports uk.co.jasonmarston.movies.annotation;
}