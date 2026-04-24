package uk.co.jasonmarston.movies.output.port;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import uk.co.jasonmarston.movies.domain.aggregate.Movie;
import uk.co.jasonmarston.movies.domain.arguments.CreateMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.DeleteMovieArgs;
import uk.co.jasonmarston.movies.domain.arguments.ReadMovieArgs;
import uk.co.jasonmarston.movies.domain.valueobject.Director;
import uk.co.jasonmarston.movies.domain.valueobject.ReleaseDate;
import uk.co.jasonmarston.movies.domain.valueobject.Title;

import java.lang.reflect.Method;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieOutputPortTest {

    @Test
    void shouldExposeExpectedMethodSignatures() throws Exception {
        assertTrue(MovieOutputPort.class.isInterface());

        Method createMovie = MovieOutputPort.class.getDeclaredMethod("createMovie", Movie.class);
        Method readMovie = MovieOutputPort.class.getDeclaredMethod("readMovie", ReadMovieArgs.class);
        Method updateMovie = MovieOutputPort.class.getDeclaredMethod("updateMovie", Movie.class);
        Method deleteMovie = MovieOutputPort.class.getDeclaredMethod("deleteMovie", DeleteMovieArgs.class);

        assertEquals(Uni.class, createMovie.getReturnType());
        assertEquals(Uni.class, readMovie.getReturnType());
        assertEquals(Uni.class, updateMovie.getReturnType());
        assertEquals(Uni.class, deleteMovie.getReturnType());
    }

    @Test
    void fakeImplementationShouldRoundTripCalls() {
        Movie movie = sampleMovie();
        ReadMovieArgs readArgs = new ReadMovieArgs(movie.getPublicId());
        DeleteMovieArgs deleteArgs = new DeleteMovieArgs(movie.getPublicId());

        FakeMovieOutputPort port = new FakeMovieOutputPort(movie);

        Movie created = port.createMovie(movie).await().indefinitely();
        Movie read = port.readMovie(readArgs).await().indefinitely();
        Movie updated = port.updateMovie(movie).await().indefinitely();
        Void deleted = port.deleteMovie(deleteArgs).await().indefinitely();

        assertSame(movie, created);
        assertSame(movie, read);
        assertSame(movie, updated);
        assertEquals(readArgs, port.lastReadArgs);
        assertEquals(deleteArgs, port.lastDeleteArgs);
        assertNull(deleted);
    }

    private static Movie sampleMovie() {
        return Movie.create(new CreateMovieArgs(
            Title.of("Arrival"),
            ReleaseDate.of(LocalDate.of(2016, 9, 2)),
            Director.of("Denis Villeneuve")
        ));
    }

    private static final class FakeMovieOutputPort implements MovieOutputPort {
        private final Movie movie;
        private ReadMovieArgs lastReadArgs;
        private DeleteMovieArgs lastDeleteArgs;

        private FakeMovieOutputPort(Movie movie) {
            this.movie = movie;
        }

        @Override
        public Uni<Movie> createMovie(Movie movie) {
            return Uni.createFrom().item(movie);
        }

        @Override
        public Uni<Movie> readMovie(ReadMovieArgs readMovieArgs) {
            this.lastReadArgs = readMovieArgs;
            return Uni.createFrom().item(movie);
        }

        @Override
        public Uni<Movie> updateMovie(Movie movie) {
            return Uni.createFrom().item(movie);
        }

        @Override
        public Uni<Void> deleteMovie(DeleteMovieArgs deleteMovieArgs) {
            this.lastDeleteArgs = deleteMovieArgs;
            return Uni.createFrom().voidItem();
        }
    }
}



