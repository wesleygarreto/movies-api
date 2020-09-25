package com.challenge.movies.api.exhibition;

import com.challenge.movies.api.movie.MovieDto;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static java.util.Objects.isNull;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/exhibitions")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExhibitionResource {

    @Inject
    private ExhibitionService exhibitionService;

    @GET
    @Operation(
            summary = "Search all exhibitions",
            description = "Search all exhibitions")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Search OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExhibitionDto.class))),

                    @APIResponse(
                            responseCode = "500",
                            description = "Internal Error",
                            content = @Content(mediaType = "text/plain"))})
    public Response searchAllExhibitions() {
        return Response.ok(exhibitionService.searchAllExhibitions()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Search a exhibition by id",
            description = "Search a exhibition by id")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Search OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "Exhibition not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "500",
                            description = "Internal Error",
                            content = @Content(mediaType = "text/plain"))})
    public Response findExhibitionById(@PathParam("id") final Long id) {

        final ExhibitionDto exhibition = exhibitionService.findExhibitionById(id);

        if (isNull(exhibition)) {
            return Response.status(NOT_FOUND).build();
        }

        return Response.ok(exhibition).build();
    }

    @POST
    @Operation(
            summary = "Create a exhibition",
            description = "Create a exhibition")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Exhibition created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "500",
                            description = "Internal Error",
                            content = @Content(mediaType = "text/plain"))})
    public Response createExhibition(@Valid final ExhibitionDto exhibitionDto) {

        final ExhibitionDto exhibition = exhibitionService.createExhibition(exhibitionDto);

        return Response.ok(exhibition).status(CREATED.getStatusCode()).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update a exhibition",
            description = "Update a exhibition")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Exhibition updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "Exhibition not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "500",
                            description = "Internal Error",
                            content = @Content(mediaType = "text/plain"))})
    public Response updateExhibition(@PathParam("id") final Long id, @Valid final ExhibitionDto exhibitionDto) {

        try {
            exhibitionService.updateExhibition(id, exhibitionDto);

        } catch (NotFoundException exception) {
            return Response.status(NOT_FOUND).build();
        }

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete a exhibition",
            description = "Delete a exhibition")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Exhibition deleted",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "Exhibition not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "500",
                            description = "Internal Error",
                            content = @Content(mediaType = "text/plain"))})
    public Response deleteExhibition(@PathParam("id") final Long id) {

        exhibitionService.deleteExhibition(id);

        return Response.ok().build();
    }

}
