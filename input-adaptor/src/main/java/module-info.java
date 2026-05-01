/**
 * Provides HTTP-facing input adapters that translate client requests into input-port
 * commands and convert handler results into transport responses.
 *
 * <p>This module contains the REST resources, request and response payload models,
 * exception mappers, and mapper configuration needed to expose the movie use cases
 * over a JSON client API.</p>
 *
 * @see uk.co.jasonmarston.movies.input.adaptor.resource.CreateMovieResource
 * @see uk.co.jasonmarston.movies.input.adaptor.resource.ReadMovieResource
 */
module input.adaptor {
    requires utility.producer;
    requires utility.exception.mapper;

    requires input.port;

    requires io.quarkus.security.api;
    requires io.smallrye.common.constraint;
    requires io.smallrye.mutiny;
    requires jakarta.cdi;
    requires jakarta.validation;
    requires jakarta.ws.rs;
    requires org.modelmapper;
    requires io.quarkus.core;
}