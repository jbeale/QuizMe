package com.quizme.api.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quizme.api.model.User;
import com.quizme.api.model.exception.DuplicateUsernameException;
import com.quizme.api.model.request.ApiClientMetadata;
import com.quizme.api.model.request.LoginRequest;
import com.quizme.api.model.response.RestResponse;
import com.quizme.api.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jbeale on 1/28/15.
 */

@Component
@Path("/auth")
public class SecurityResource {
    private Gson gson;
    private UserService userService;
    //private LoginValidator loginValidator;

    public SecurityResource() {
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Autowired
    public void setUserService(UserService service)
    {
        this.userService = service;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginPost(@FormParam("username") String username, @FormParam("password") String password, @Context HttpServletRequest request)
    {
        return this.login(username, password, request);
    }

    @GET
    @Path("/login/{username}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("username") String username, @PathParam("password") String password, @Context HttpServletRequest request)
    {
        LoginRequest command = new LoginRequest(username, password, false);

        Errors errors = new BeanPropertyBindingResult(command, "login");

        if (errors.hasErrors()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(new RestResponse(400, false, "Bad authentication request."))).build();
        }

        UsernamePasswordToken token = new UsernamePasswordToken(command.getUsername(), command.getPassword(), command.isRemembered());
        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            errors.reject("error.login.generic", e.getMessage());
        }

        if (errors.hasErrors()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(new RestResponse(403, false, errors.getAllErrors().get(0).getDefaultMessage()))).build();
        }

        ApiClientMetadata clientMetadata = new ApiClientMetadata(request.getRemoteAddr(), request.getRemoteHost(), request.getHeader("User-Agent"));

        User u = userService.getCurrentUser();
        String authToken = userService.getNewToken(u.getId(), clientMetadata);

        Map response = new HashMap<String, Object>();
        response.put("authToken", authToken);
        response.put("user", u);

        return Response.ok(gson.toJson(new RestResponse(200, true, response))).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newAccount(@FormParam("username") String username, @FormParam("password") String password,
                               @FormParam("firstname") String firstname, @FormParam("lastname") String lastname,
                               @FormParam("email") String email, @Context HttpServletRequest request) {

        User u = new User();
        u.setUsername(username);
        u.setFirstname(firstname);
        u.setLastname(lastname);
        u.setEmail(email);

        try {
            userService.addUser(u, password);
        } catch (DuplicateUsernameException e) {
            return Response.status(Response.Status.CONFLICT).entity(gson.toJson(new RestResponse(409, false, "Username \""+e.username+"\" is not unique. Try a different one."))).build();
        } catch (Exception e) {
            return Response.serverError().entity(gson.toJson(new RestResponse(500, false, e.getMessage()))).build();
        }

        //If the account was successfully created, log it in and return auth info.
        return this.login(username, password, request);
        /*return Response.ok().entity(
                gson.toJson(new RestResponse(200, true, response))
        );*/
    }

    @GET
    @Path("/tempcreate/{username}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tempCreate(@PathParam("username") String username, @PathParam("password") String password) {
        userService.createUser(username, password, "test@email.not");
        return Response.ok().entity("CREATED").build();
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() {
        SecurityUtils.getSubject().logout();

        RestResponse response = new RestResponse(200, true, "Logged Out");

        return Response.ok("SUBJECT LOGGED OUT").build();
    }

    @GET
    @Path("/token/{authToken}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByToken(@PathParam("authToken") String token) {
        User u = userService.validateToken(token);
        if (u == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(new RestResponse(404, false, "Token expired, invalid, or null."))).build();
        }
        return Response.ok(gson.toJson(new RestResponse(200, true, u))).build();
    }
}
