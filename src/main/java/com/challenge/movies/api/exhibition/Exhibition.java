package com.challenge.movies.api.exhibition;

import com.challenge.movies.api.movie.Movie;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

import static com.challenge.movies.api.exhibition.Exhibition.SEARCH_ALL_QUERY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQuery(name = SEARCH_ALL_QUERY, query = "SELECT p FROM Exhibition p")
public class Exhibition implements Serializable {

    public static final String SEARCH_ALL_QUERY = "Exhibition.searchAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private String time;

    @NotNull
    @ManyToOne
    private Movie movie;

    public Exhibition(final ExhibitionDto exhibitionDto) {

        this.date = exhibitionDto.getDate();
        this.time = exhibitionDto.getTime();
        this.movie = new Movie(exhibitionDto.getMovieDto());
    }
}
