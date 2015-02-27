package com.quizme.api.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quizme.api.model.Question;
import com.quizme.api.model.User;
import com.quizme.api.model.canonical.MultipleChoice;
import com.quizme.api.model.canonical.Option;
import com.quizme.api.model.response.RestResponse;
import com.quizme.api.service.QuestionService;
import com.quizme.api.service.UserService;
import com.quizme.api.util.UnixTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbeale on 2/27/15.
 */

@Component
@Path("/question")
public class QuestionResource {
    private Gson gson;
    private UserService userService;
    private QuestionService questionService;

    public QuestionResource() {
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
    }

    @Autowired
    public void setUserService(UserService service)
    {
        this.userService = service;
    }

    @Autowired
    public void setQuestionService(QuestionService service) {
        this.questionService = service;
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyQuestions(@PathParam("id") int id, @HeaderParam("token") String token) {
        User u = userService.validateToken(token);
        if (u == null)
            return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(new RestResponse(401, false, "Expired, invalid, or null token."))).build();

        List<Question> questionList = questionService.getQuestionsByUser(u.getId());
        return Response.ok(gson.toJson(new RestResponse(200, true, questionList))).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestion(@PathParam("id") int id) {
        Question q = questionService.getQuestion(id);
        if (q == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(new RestResponse(404, false, q))).build();
        }
        return Response.ok(gson.toJson(new RestResponse(200, true, q))).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteQuestion(@PathParam("id") int id, @HeaderParam("token") String token) {
        User u = userService.validateToken(token);
        if (u == null)
            return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(new RestResponse(401, false, "Expired, invalid, or null token."))).build();

        Question q = questionService.getQuestion(id);
        if (q == null)
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(new RestResponse(404, false, "Question not found"))).build();

        if (q.getAuthorUserId() != u.getId())
            return Response.status(Response.Status.FORBIDDEN).entity(gson.toJson(new RestResponse(403, false, "User associated with token cannot delete this question."))).build();

        questionService.delete(id);

        return Response.ok(gson.toJson(new RestResponse(200, true, "Question Deleted."))).build();
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editQuestion(@PathParam("id") int id, @HeaderParam("token") String token, @FormParam("questionName") String questionName, @FormParam("questionType") String questionType,
                                 @FormParam("promptText") String promptText, @FormParam("optionTexts[]") List<String> optionTexts, @FormParam("correctOptionIndex") int correctOptionIndex) {
        User u = userService.validateToken(token);
        if (u == null)
            return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(new RestResponse(401, false, "Expired, invalid, or null token."))).build();

        Question q = questionService.getQuestion(id);
        if (q == null)
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(new RestResponse(404, false, "Question not found"))).build();

        if (q.getAuthorUserId() != u.getId())
            return Response.status(Response.Status.FORBIDDEN).entity(gson.toJson(new RestResponse(403, false, "User associated with token cannot update this question."))).build();

        q.setName(questionName);
        q.setType(questionType);
        q.setModified(UnixTime.get());

        MultipleChoice mc = new MultipleChoice();
        mc.prompt = promptText;
        mc.choices = new ArrayList<Option>();
        for(int i = 0; i < optionTexts.size(); i++) {
            Option o = new Option();
            o.text = optionTexts.get(i);
            if (i == correctOptionIndex) {
                o.correct = true;
            }
            mc.choices.add(o);
        }
        q.setData(mc);

        questionService.save(q);

        return Response.ok(gson.toJson(new RestResponse(200, true, q))).build();
    }

    @PUT
    @Path("/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createQuestion(@HeaderParam("token") String token, @FormParam("questionName") String questionName, @FormParam("questionType") String questionType,
                                   @FormParam("promptText") String promptText, @FormParam("optionTexts[]") List<String> optionTexts, @FormParam("correctOptionIndex") int correctOptionIndex) {
        User u = userService.validateToken(token);
        if (u == null)
            return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(new RestResponse(401, false, "Expired, invalid, or null token."))).build();
        Question q = new Question();
        q.setAuthorUserId(u.getId());
        q.setCreated(UnixTime.get());
        q.setModified(UnixTime.get());
        q.setName(questionName);
        q.setType(questionType);

        MultipleChoice mc = new MultipleChoice();
        mc.prompt = promptText;
        mc.choices = new ArrayList<Option>();
        for(int i = 0; i < optionTexts.size(); i++) {
            Option o = new Option();
            o.text = optionTexts.get(i);
            if (i == correctOptionIndex) {
                o.correct = true;
            }
            mc.choices.add(o);
        }
        q.setData(mc);

        questionService.save(q);

        return Response.ok(gson.toJson(new RestResponse(200, true, q))).build();

    }

}
