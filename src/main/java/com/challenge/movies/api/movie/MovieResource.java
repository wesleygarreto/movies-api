package com.challenge.movies.api.movie;

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

@Path("/movies")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    private MovieService movieService;

    @GET
    @Operation(
            summary = "Search all movies",
            description = "Search all movies")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Search OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),

                    @APIResponse(
                            responseCode = "500",
                            description = "Internal Error",
                            content = @Content(mediaType = "text/plain"))})
    public Response searchAllMovies() {
        return Response.ok(movieService.searchAllMovies()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Search a movies by id",
            description = "Search a movies by id")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Search OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "Movie not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "500",
                            description = "Internal Error",
                            content = @Content(mediaType = "text/plain"))})
    public Response findMovieById(@PathParam("id") final Long id) {

        final MovieDto movie = movieService.findMovieById(id);

        if (isNull(movie)) {
            return Response.status(NOT_FOUND).build();
        }

        return Response.ok(movie).build();
    }

    @POST
    @Operation(
            summary = "Create a movie",
            description = "Create a movie")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Movie created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),

                    @APIResponse(
                            responseCode = "500",
                            description = "Internal Error",
                            content = @Content(mediaType = "text/plain"))})
    public Response createMovie(@Valid final MovieDto movieDto) {

        final MovieDto movie = movieService.createMovie(movieDto);

        return Response.ok(movie).status(CREATED.getStatusCode()).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update a movie",
            description = "Update a movie")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Movie updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "Movie not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "500",
                            description = "Internal Error",
                            content = @Content(mediaType = "text/plain"))})
    public Response updateMovie(@PathParam("id") final Long id, @Valid final MovieDto movieDto) {

        try {
            movieService.updateMovie(id, movieDto);

        } catch (NotFoundException exception) {
            return Response.status(NOT_FOUND).build();
        }

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete a movie",
            description = "Delete a movie")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Movie deleted",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "Movie not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDto.class))),
                    @APIResponse(
                            responseCode = "500",
                            description = "Internal Error",
                            content = @Content(mediaType = "text/plain"))})
    public Response deleteMovie(@PathParam("id") final Long id) {

        movieService.deleteMovie(id);

        return Response.ok().build();
    }

}
