module output.port {
    requires transitive application.wiring;
    requires transitive io.smallrye.mutiny;

    exports uk.co.jasonmarston.movies.output.port;
}