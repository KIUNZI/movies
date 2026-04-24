/**
 * Declares output-side contracts for persisting and retrieving movie aggregates.
 *
 * <p>This module exposes the outbound port interfaces consumed by input-port handlers
 * and implemented by output adapters, isolating persistence details behind reactive
 * use-case-facing abstractions.</p>
 *
 * @see uk.co.jasonmarston.movies.output.port.MovieOutputPort
 */
module output.port {
    requires transitive application.wiring;
    requires transitive io.smallrye.mutiny;

    exports uk.co.jasonmarston.movies.output.port;
}