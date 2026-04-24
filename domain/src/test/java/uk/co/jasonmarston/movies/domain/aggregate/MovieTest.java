package uk.co.jasonmarston.movies.domain.aggregate;

import org.junit.jupiter.api.Test;
import uk.co.jasonmarston.movies.domain.arguments.CreateMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.UpdateMovieArgs;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovieTest {

    @Test
    void shouldCreateMovieWithGeneratedPublicIdAndInitialValues() {
        CreateMovieArgs args = new CreateMovieArgs(
            Title.of("Interstellar"),
            ReleaseDate.of(LocalDate.of(2014, 11, 7)),
            Director.of("Christopher Nolan")
        );

        Movie movie = Movie.create(args);

        assertNotNull(movie.getPublicId());
        assertEquals(args.title(), movie.getTitle());
        assertEquals(args.release(), movie.getRelease());
        assertEquals(args.director(), movie.getDirector());
    }

    @Test
    void shouldRejectNullCreateArgs() {
        assertThrows(
            NullPointerException.class,
            () -> Movie.create(null)
        );
    }

    @Test
    void shouldUpdateMutableFieldsButKeepPublicId() {
        Movie movie = Movie.create(new CreateMovieArgs(
            Title.of("Old Title"),
            ReleaseDate.of(LocalDate.of(2010, 1, 1)),
            Director.of("Old Director")
        ));

        var initialPublicId = movie.getPublicId();
        UpdateMovieArgs update = new UpdateMovieArgs(
            Title.of("New Title"),
            ReleaseDate.of(LocalDate.of(2011, 2, 2)),
            Director.of("New Director")
        );

        movie.update(update);

        assertSame(initialPublicId, movie.getPublicId());
        assertEquals(update.title(), movie.getTitle());
        assertEquals(update.release(), movie.getRelease());
        assertEquals(update.director(), movie.getDirector());
    }

    @Test
    void shouldRejectNullUpdateArgs() {
        Movie movie = Movie.create(new CreateMovieArgs(
            Title.of("Title"),
            ReleaseDate.of(LocalDate.of(2010, 1, 1)),
            Director.of("Director")
        ));

        assertThrows(
            NullPointerException.class,
            () -> movie.update(null)
        );
    }
}

