/**
 * Declares the input-side use-case contracts for the movie application.
 *
 * <p>This module exposes command models, query/view models, handler interfaces, and
 * their default implementations so transport adapters can invoke application use
 * cases without depending directly on persistence concerns.</p>
 *
 * @see uk.co.jasonmarston.movies.input.port.CreateMovieHandler
 * @see uk.co.jasonmarston.movies.input.port.command.CreateMovieCommand
 * @see uk.co.jasonmarston.movies.input.port.handler.DefaultCreateMovieHandler
 */
module input.port {
    requires transitive domain;

    requires utility.producer;
    requires utility.validator;
    requires utility.domain.exception;

    requires static lombok;

    requires jakarta.cdi;
    requires jakarta.validation;
    requires org.modelmapper;
    requires output.port;

    requires transitive io.smallrye.mutiny;

    exports uk.co.jasonmarston.movies.input.port;
    exports uk.co.jasonmarston.movies.input.port.command;
    exports uk.co.jasonmarston.movies.input.port.view;
    exports uk.co.jasonmarston.movies.input.port.handler;
}