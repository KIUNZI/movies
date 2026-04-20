module application.wiring {
    requires jakarta.cdi;
    requires jakarta.validation;
    requires org.modelmapper;
    requires org.modelmapper.module.record;
    requires transitive domain;

    exports uk.co.jasonmarston.movies.annotation;
}