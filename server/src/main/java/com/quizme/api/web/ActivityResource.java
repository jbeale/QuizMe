package com.quizme.api.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quizme.api.model.Activity;
import com.quizme.api.model.User;
import com.quizme.api.model.response.RestResponse;
import com.quizme.api.service.ActivityService;
import com.quizme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jbeale on 3/6/15.
 */
@Component
@Path("/activity")
public class ActivityResource {
    private Gson gson;

    private UserService userService;
    private ActivityService activityService;

    public ActivityResource() {
        gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Autowired
    public void setActivityService(ActivityService as) {
        this.activityService = as;
    }

    @Autowired
    public void setUserService(UserService us) {
        this.userService = us;
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listMyActivities(@HeaderParam("token") String token) {
        User u = userService.validateToken(token);
        if (u == null)
            return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(new RestResponse(401, false, "Expired, invalid, or null token."))).build();

        List<Activity> activityList = activityService.listActivitiesByUser(u.getId());
        return Response.ok(gson.toJson(new RestResponse(200, true, activityList))).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivity(@HeaderParam("token") String token, @PathParam("id") int id) {
        User u = userService.validateToken(token);
        if (u == null)
            return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(new RestResponse(401, false, "Expired, invalid, or null token."))).build();
        Activity activity = activityService.getActivity(id);

        if (activity == null)
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(new RestResponse(404, false, "Activity not found"))).build();

        if (activity.getUserId() != u.getId())
            return Response.status(Response.Status.FORBIDDEN).entity(gson.toJson(new RestResponse(403, false, "Activity does not belong to tokened user."))).build();

        return Response.ok(gson.toJson(new RestResponse(200, true, activity))).build();
    }
}
