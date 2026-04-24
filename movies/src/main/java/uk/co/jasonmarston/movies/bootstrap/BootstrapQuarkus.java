package uk.co.jasonmarston.movies.bootstrap;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Quarkus application bootstrap entry point for the Movies service.
 *
 * <p>This class is discovered via {@link QuarkusMain} and is responsible for logging
 * startup intent before delegating lifecycle management to {@link Quarkus}.</p>
 *
 * @see Quarkus
 */
@QuarkusMain
public class BootstrapQuarkus {
    private BootstrapQuarkus() {
    }

    /**
     * Starts the Movies application under Quarkus runtime control.
     *
     * @param args the command-line arguments forwarded to the Quarkus launcher
     */
    public static void main(final String[] args) {
        Log.info("Starting Movies Application");
        Quarkus.run(args);
    }
}
