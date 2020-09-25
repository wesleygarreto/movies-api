package com.challenge.movies.api.exhibition;

import com.challenge.movies.api.movie.Movie;
import com.challenge.movies.api.movie.MovieService;

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
public class ExhibitionService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ExhibitionDto> searchAllExhibitions() {

        final List<Exhibition> exhibitions = entityManager.createNamedQuery("Exhibition.searchAll", Exhibition.class).getResultList();

        return convertToDto(exhibitions);
    }


    public ExhibitionDto findExhibitionById(final Long id) {
        return convertToDto(entityManager.find(Exhibition.class, id));
    }

    public ExhibitionDto createExhibition(@Valid final ExhibitionDto exhibitionDto) {

        final Exhibition exhibition = new Exhibition(exhibitionDto);
        entityManager.persist(exhibition);
        entityManager.flush();
        exhibitionDto.setId(exhibition.getId());

        return exhibitionDto;
    }

    public void updateExhibition(final Long id, final ExhibitionDto exhibitionDto) {

        final Exhibition exhibitionToUpdate = entityManager.find(Exhibition.class, id);

        if (isNull(exhibitionToUpdate)) {
            throw new NotFoundException();
        }

        exhibitionToUpdate.setDate(exhibitionDto.getDate());
        exhibitionToUpdate.setTime(exhibitionDto.getTime());
        exhibitionToUpdate.setMovie(new Movie(exhibitionDto.getMovieDto()));
    }

    public void deleteExhibition(final Long id) {

        final Exhibition exhibitionToDelete = entityManager.find(Exhibition.class, id);

        if (nonNull(exhibitionToDelete)) {
            entityManager.remove(exhibitionToDelete);
        }
    }

    private List<ExhibitionDto> convertToDto(final List<Exhibition> exhibitions) {

        final ArrayList<ExhibitionDto> exhibitionDtoList = new ArrayList<>();
        exhibitions.forEach(exhibition -> exhibitionDtoList.add(convertToDto(exhibition)));

        return exhibitionDtoList;
    }

    private ExhibitionDto convertToDto(final Exhibition exhibition) {
        return isNull(exhibition) ? null : ExhibitionDto.builder()
                .id(exhibition.getId())
                .date(exhibition.getDate())
                .time(exhibition.getTime())
                .movieDto(MovieService.convertToDto(exhibition.getMovie()))
                .build();
    }
}
