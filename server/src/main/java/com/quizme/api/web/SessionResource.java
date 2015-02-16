package com.quizme.api.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quizme.api.model.QuizSession;
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
        this.gson = new GsonBuilder().create();
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
    @Path("/get/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessionInfo(@PathParam("code") String code) {

        return null;
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startNewSession(@FormParam("name") String sessionName,
                                    @FormParam("token") String authToken) {
        User currentUser = userService.validateToken(authToken);
        if (currentUser == null) {
            return Response.status(403).entity(gson.toJson(new RestResponse(403, false, "Token is invalid. User not authorized."))).build();
        }

        QuizSession quizSession = sessionService.createSession(sessionName, currentUser.getId());

        return Response.ok(gson.toJson(new RestResponse(200, true, quizSession))).build();
    }
}
