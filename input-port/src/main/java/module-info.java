module input.port {
    requires static lombok;
    requires jakarta.cdi;
    requires jakarta.validation;
    requires org.modelmapper;
    requires output.port;
    requires transitive application.wiring;
    requires transitive io.smallrye.mutiny;

    exports uk.co.jasonmarston.movies.input.port;
    exports uk.co.jasonmarston.movies.input.port.command;
    exports uk.co.jasonmarston.movies.input.port.view;
    exports uk.co.jasonmarston.movies.input.port.handler;
}