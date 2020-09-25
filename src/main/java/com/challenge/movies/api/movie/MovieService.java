package com.challenge.movies.api.movie;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class MovieService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<MovieDto> searchAllMovies() {

        final List<Movie> movies = entityManager.createNamedQuery("Movie.searchAll", Movie.class).getResultList();

        return convertToDto(movies);
    }


    public MovieDto findMovieById(final Long id) {
        return convertToDto(entityManager.find(Movie.class, id));
    }

    public MovieDto createMovie(@Valid final MovieDto movieDto) {

        final Movie movie = new Movie(movieDto);
        entityManager.persist(movie);
        entityManager.flush();
        movieDto.setId(movie.getId());

        return movieDto;
    }

    public void updateMovie(final Long id, final MovieDto movieDto) {

        final Movie movieToUpdate = entityManager.find(Movie.class, id);

        if (isNull(movieToUpdate)) {
            throw new NotFoundException();
        }

        movieToUpdate.setName(movieDto.getName());
        movieToUpdate.setGenre(movieDto.getGenre());
        movieToUpdate.setDirectorName(movieDto.getDirectorName());
        movieToUpdate.setReleaseDate(movieDto.getReleaseDate());
    }

    public void deleteMovie(final Long id) {

        final Movie movieToDelete = entityManager.find(Movie.class, id);

        if (nonNull(movieToDelete)) {
            entityManager.remove(movieToDelete);
        }
    }

    public static List<MovieDto> convertToDto(final List<Movie> movies) {

        final ArrayList<MovieDto> movieDtoList = new ArrayList<>();
        movies.forEach(movie -> movieDtoList.add(convertToDto(movie)));

        return movieDtoList;
    }

    public static MovieDto convertToDto(final Movie movie) {
        return isNull(movie) ? null : MovieDto.builder()
                                              .id(movie.getId())
                                              .name(movie.getName())
                                              .genre(movie.getGenre())
                                              .directorName(movie.getDirectorName())
                                              .releaseDate(movie.getReleaseDate())
                                              .build();
    }
}
