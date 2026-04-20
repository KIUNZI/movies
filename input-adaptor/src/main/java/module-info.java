module input.adaptor {
    requires input.port;
    requires io.quarkus.security.api;
    requires io.smallrye.common.constraint;
    requires io.smallrye.mutiny;
    requires jakarta.cdi;
    requires jakarta.validation;
    requires jakarta.ws.rs;
    requires org.modelmapper;
    requires quarkus.core;
}