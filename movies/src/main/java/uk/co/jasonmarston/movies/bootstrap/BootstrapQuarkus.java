package uk.co.jasonmarston.movies.bootstrap;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class BootstrapQuarkus {
    static void main(final String[] args) {
        Log.info("Starting Movies Application");
        Quarkus.run(args);
    }
}
