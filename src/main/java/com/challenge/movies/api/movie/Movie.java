package com.challenge.movies.api.movie;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import static com.challenge.movies.api.movie.Movie.SEARCH_ALL_QUERY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = SEARCH_ALL_QUERY, query = "SELECT p FROM Movie p")
public class Movie implements Serializable {

    public static final String SEARCH_ALL_QUERY = "Movie.searchAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String directorName;

    @Column(nullable = false)
    private LocalDate releaseDate;

    public Movie(final MovieDto movieDto) {

        this.name = movieDto.getName();
        this.genre = movieDto.getGenre();
        this.directorName = movieDto.getDirectorName();
        this.releaseDate = movieDto.getReleaseDate();

    }
}
