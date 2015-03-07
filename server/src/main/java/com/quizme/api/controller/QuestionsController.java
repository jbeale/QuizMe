package com.quizme.api.controller;

import com.quizme.api.model.Question;
import com.quizme.api.model.User;
import com.quizme.api.model.canonical.MultipleChoice;
import com.quizme.api.model.canonical.Option;
import com.quizme.api.model.exception.ResourceNotFoundException;
import com.quizme.api.model.exception.UnauthorizedResourceAccessException;
import com.quizme.api.service.QuestionService;
import com.quizme.api.service.UserService;
import com.quizme.api.util.UnixTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbeale on 2/20/15.
 */
@Controller
@Component
@RequestMapping("/questions")
public class QuestionsController {

    private QuestionService questionService;
    private UserService userService;

    public boolean checkAuth() {
        //todo use Shiro filters for this
        User user = userService.getCurrentUser();
        return user != null;
    }
    public String redirect() {
        return "redirect:/auth/login";
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/list")
    public String listMyQuestions(Model model) {
        if (!checkAuth()) return redirect();
        int userId = userService.getCurrentUser().getId();
        List<Question> questionList = questionService.getQuestionsByUser(userId);

        model.addAttribute("questions", questionList);
        model.addAttribute("titlebar", "My Questions");
        return "questionlist";
    }

    @RequestMapping(value="/new", method=RequestMethod.GET)
    public String createQuestion(Model model) {
        if (!checkAuth()) return redirect();
        model.addAttribute("titlebar", "New Question");
        return "questionedit";
    }

    @RequestMapping(value="/new", method=RequestMethod.POST)
    public String createQuestionSubmit(@RequestParam("questionName") String questionName, @RequestParam("questionType") String questionType,
                                       @RequestParam("promptText") String prompt, @RequestParam("optionText[]") String[] options, @RequestParam("correctOption") int correctOptionIndex,
                                       Model model) {
        if (!checkAuth()) return redirect();
        Question q = new Question();
        q.setAuthorUserId(userService.getCurrentUser().getId());
        q.setCreated(UnixTime.get());
        q.setModified(UnixTime.get());
        q.setName(questionName);
        q.setType(questionType);
        q.setModified(UnixTime.get());

        MultipleChoice mc = new MultipleChoice();
        mc.prompt = prompt;
        mc.choices = new ArrayList<Option>();

        for(int i = 0; i<options.length; i++) {
            Option o = new Option();
            o.text = options[i];
            o.correct = (correctOptionIndex == i);
            mc.choices.add(o);
        }

        q.setData(mc);

        questionService.save(q);

        return "redirect:/questions/list";
    }

    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String editQuestion(@PathVariable("id") int questionId, Model model) throws ResourceNotFoundException, UnauthorizedResourceAccessException {
        if (!checkAuth()) return redirect();
        model.addAttribute("titlebar", "Edit Question");

        Question q = questionService.getQuestion(questionId);

        if (q.getAuthorUserId() != userService.getCurrentUser().getId())
            throw new UnauthorizedResourceAccessException();
        if (q == null)
            throw new ResourceNotFoundException();

        model.addAttribute("question", q);

        return "questionedit";
    }

    @RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
    public String editQuestionSubmit(@PathVariable("id") int questionId, @RequestParam("questionName") String questionName, @RequestParam("questionType") String questionType,
                                     @RequestParam("promptText") String prompt, @RequestParam("optionText[]") String[] options, @RequestParam("correctOption") int correctOptionIndex,
                                     Model model) throws ResourceNotFoundException, UnauthorizedResourceAccessException {
        if (!checkAuth()) return redirect();
        Question q = questionService.getQuestion(questionId);

        if (q.getAuthorUserId() != userService.getCurrentUser().getId())
            throw new UnauthorizedResourceAccessException();
        if (q == null)
            throw new ResourceNotFoundException();

        q.setName(questionName);
        q.setType(questionType);
        q.setModified(UnixTime.get());

        MultipleChoice mc = new MultipleChoice();
        mc.prompt = prompt;
        mc.choices = new ArrayList<Option>();

        for(int i = 0; i<options.length; i++) {
            Option o = new Option();
            o.text = options[i];
            o.correct = (correctOptionIndex == i);
            mc.choices.add(o);
        }

        q.setData(mc);

        questionService.save(q);

        return "redirect:/questions/list";
    }

    @RequestMapping(value="/delete/{id}")
    public String deleteQuestion(@PathVariable("id") int questionId) throws ResourceNotFoundException, UnauthorizedResourceAccessException {
        if (!checkAuth()) return redirect();
        Question q = questionService.getQuestion(questionId);

        if (q.getAuthorUserId() != userService.getCurrentUser().getId())
            throw new UnauthorizedResourceAccessException();
        if (q == null)
            throw new ResourceNotFoundException();

        questionService.delete(questionId);
        return "redirect:/questions/list";
    }
}
