package com.challenge.movies.api.movie;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto implements Serializable {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String genre;

    @NotBlank
    private String directorName;

    @NotNull
    private LocalDate releaseDate;

}
