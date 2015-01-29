package com.quizme.api.web;

import com.google.gson.Gson;
import com.quizme.api.model.response.RestResponse;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jbeale on 1/29/15.
 */

@Component
@Path("/meta")
public class MetaResource {

    private Gson gson;

    public MetaResource() {
        this.gson = new Gson();
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() {

        RestResponse response = new RestResponse(200, true, "Hello world!");

        return Response.ok(gson.toJson(response)).build();
    }
}
