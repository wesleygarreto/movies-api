package com.challenge.movies.api;

import com.challenge.movies.api.movie.Movie;
import com.challenge.movies.api.movie.MovieDto;
import com.challenge.movies.api.movie.MovieResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jaxrs.RESTClient;
import org.microshed.testing.jupiter.MicroShedTest;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicroShedTest
@SharedContainerConfig(SampleApplicationConfig.class)
public class MovieResourceIT {

    @RESTClient
    public static MovieResource movieResource;

    @AfterEach
    public void cleanUp() {

        for (final Movie movie : movieResource.searchAllMovies().readEntity(new GenericType<List<Movie>>() {
        })) {
            movieResource.deleteMovie(movie.getId());
        }
    }

    @Test
    public void shouldReturnAllMovies() {

        movieResource.createMovie(MovieDto.builder().name("Movie 1").genre("Genre 1").directorName("Director 1").releaseDate(LocalDate.now()).build());
        movieResource.createMovie(MovieDto.builder().name("Movie 2").genre("Genre 2").directorName("Director 2").releaseDate(LocalDate.now()).build());
        movieResource.createMovie(MovieDto.builder().name("Movie 3").genre("Genre 3").directorName("Director 3").releaseDate(LocalDate.now()).build());

        final Response allMovies = movieResource.searchAllMovies();

        assertEquals(3, allMovies.readEntity(new GenericType<List<Movie>>() {
        }).size());
    }

    @Test
    public void shouldCreateMovie() {

        final Response response = movieResource.createMovie(
                MovieDto.builder()
                        .name("Movie 1")
                        .genre("Genre 1")
                        .directorName("Director 1")
                        .releaseDate(LocalDate.now())
                        .build());

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldUpdatePerson() {

        final Movie createdMovie = movieResource.createMovie(
                MovieDto.builder()
                        .name("Movie 1")
                        .genre("Genre 1")
                        .directorName("Director 1")
                        .releaseDate(LocalDate.now())
                        .build()).readEntity(Movie.class);

        movieResource.updateMovie(createdMovie.getId(),
                MovieDto.builder()
                        .name("Movie 2")
                        .genre("Genre 2")
                        .directorName("Director 2")
                        .releaseDate(LocalDate.now())
                        .build());

        final Movie movie = movieResource.findMovieById(createdMovie.getId()).readEntity(Movie.class);

        assertEquals("Movie 2", movie.getName());
    }

    @Test
    public void shouldDeleteMovie() {

        final Movie createdMovie = movieResource.createMovie(
                MovieDto.builder()
                        .name("Movie 1")
                        .genre("Genre 1")
                        .directorName("Director 1")
                        .releaseDate(LocalDate.now())
                        .build()).readEntity(Movie.class);

        movieResource.deleteMovie(createdMovie.getId());

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), movieResource.findMovieById(createdMovie.getId()).getStatus());
    }

}
