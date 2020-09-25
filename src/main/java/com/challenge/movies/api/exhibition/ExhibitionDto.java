package com.challenge.movies.api.exhibition;

import com.challenge.movies.api.movie.MovieDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExhibitionDto implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private String time;

    @NotNull
    private MovieDto movieDto;
}
