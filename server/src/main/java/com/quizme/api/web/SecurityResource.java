package com.quizme.api.web;

import com.google.gson.Gson;
import com.quizme.api.model.request.LoginRequest;
import com.quizme.api.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        this.gson = new Gson();
    }

    @Autowired
    public void setUserService(UserService service)
    {

    }

    @GET
    @Path("/login/{username}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("username") String username, @PathParam("password") String password)
    {
        LoginRequest command = new LoginRequest(username, password, false);

        Errors errors = new BeanPropertyBindingResult(command, "login");

        if (errors.hasErrors()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("FAIL").build();
        }

        UsernamePasswordToken token = new UsernamePasswordToken(command.getUsername(), command.getPassword(), command.isRemembered());
        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            errors.reject("error.login.generic", e.getMessage());
        }

        if (errors.hasErrors()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(errors.getAllErrors().get(0).getDefaultMessage()).build();
        }

        return Response.ok("ACCEPTED").build();
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() {
        SecurityUtils.getSubject().logout();

        return Response.ok("SUBJECT LOGGED OUT").build();
    }
}
