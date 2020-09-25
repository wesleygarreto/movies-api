package com.challenge.movies.api.exhibition;

import com.challenge.movies.api.movie.MovieDto;
import lombok.*;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
