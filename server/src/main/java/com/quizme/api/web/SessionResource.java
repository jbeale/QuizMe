package com.quizme.api.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quizme.api.model.Session;
import com.quizme.api.model.User;
import com.quizme.api.model.response.RestResponse;
import com.quizme.api.service.SessionService;
import com.quizme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jbeale on 2/7/15.
 */

@Component
@Path("/session")
public class SessionResource {

    private UserService userService;
    private SessionService sessionService;
    private Gson gson;

    public SessionResource() {
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
    }

    @Autowired
    public void setUserService (UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSessionService (SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessionInfo(@PathParam("id") int id) {
        Session session = sessionService.getSessionById(id);
        if (session == null) {
            return Response.status(404).entity(gson.toJson(new RestResponse(404, false, "Session not found."))).build();
        }
        return Response.ok(gson.toJson(new RestResponse(200, true, session))).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startNewSession(@FormParam("name") String sessionName, @FormParam("activityId") int activityId,
                                    @HeaderParam("token") String authToken) {
        User currentUser = userService.validateToken(authToken);
        if (currentUser == null) {
            return Response.status(403).entity(gson.toJson(new RestResponse(403, false, "Token is invalid. User not authorized."))).build();
        }

        Session quizSession = sessionService.createSession(sessionName, currentUser.getId(), activityId);

        return Response.ok(gson.toJson(new RestResponse(200, true, quizSession))).build();
    }

    @GET
    @Path("/join/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinSession(@PathParam("code") String code) {
        code = code.replaceAll("[^\\d.]", "");
        if (code.length() != 9) {
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(new RestResponse(400, false, "Session code had a bad format or was incomplete."))).build();
        }
        int intCode = Integer.parseInt(code);
        Session quizSession = sessionService.getSessionByCode(intCode);
        if (quizSession == null) {
            return Response.status(404).entity(gson.toJson(new RestResponse(404, false, "Session not found."))).build();
        }
        return Response.ok(gson.toJson(new RestResponse(200, true, quizSession))).build();
    }

}
