package com.coffeeandit.quarkus.sample;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.quarkus.panache.common.Sort;


@Path("/api")
@Produces("application/json")
@Consumes("application/json")
public class MovieResource {

    @OPTIONS
    public Response opt() {
        return Response.ok().build();
    }

    @GET
    public List<Movie> getAll() {
        return Movie.listAll(Sort.by("year"));
    }

    @GET
    @Path("/{id}")
    public Movie getOne(@PathParam("id") Long id) {
        Movie entity = Movie.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Movie with id of " + id + " does not exist.", Status.NOT_FOUND);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(@Valid Movie item) {
        item.persist();
        return Response.status(Status.CREATED).entity(item).build();
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public Response update(@Valid Movie movie, @PathParam("id") Long id) {
        Movie entity = Movie.findById(id);
        entity.id = id;
        entity.completed = movie.completed;
        entity.year = movie.year;
        entity.title = movie.title;
        entity.url = movie.url;
        return Response.ok(entity).build();
    }

    @DELETE
    @Transactional
    public Response deleteCompleted() {
        Movie.deleteCompleted();
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Long id) {
        Movie entity = Movie.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Movie with id of " + id + " does not exist.", Status.NOT_FOUND);
        }
        entity.delete();
        return Response.noContent().build();
    }

}