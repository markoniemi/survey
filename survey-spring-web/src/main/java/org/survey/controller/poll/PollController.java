package org.survey.controller.poll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.survey.model.poll.Poll;
import org.survey.model.poll.Question;
import org.survey.model.user.Role;
import org.survey.service.poll.PollService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class PollController {
    @Resource
    private MessageSource messageSource;
    @Resource
    private PollService pollService;

    @PostMapping(value = "/poll/save")
    public String savePoll(@ModelAttribute Poll poll) {
        log.debug("savePoll - saving poll:" + poll);
        if (poll.getId() != null) {
            pollService.update(poll);
        } else {
            pollService.create(poll);
        }
        return "redirect:/poll/polls";
    }

    @GetMapping(value = "/poll/new")
    public ModelAndView newPoll() {
        return editPoll(null);
    }

    @GetMapping(value = "/poll/{id}")
    public ModelAndView editPoll(@PathVariable Long id) {
        Poll poll=null;
        if (id!=null) {
            poll = pollService.findById(id);
        }
        log.debug("editPoll() - found poll: " + poll);
        if (poll == null) {
            poll = new Poll();
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("/poll/poll");
        model.addObject("poll", poll);
        return model;
    }

    @PostMapping(value = "/poll/delete/{id}")
    public String deletePoll(@PathVariable Long id) {
        pollService.delete(id);
        return "redirect:/poll/polls";
    }

    // TODO use GET
    @PostMapping(value = "/poll/addQuestion")
    public ModelAndView addQuestion(@ModelAttribute Poll poll) {
        // Poll poll = pollService.findOne(name);
        log.debug("editPoll() - found poll: " + poll);
        if (poll != null) {
            if (poll.getQuestions() == null) {
                poll.setQuestions(new ArrayList<Question>());
            }
            Question question = new Question();
            question.setPoll(poll);
            poll.getQuestions().add(question);
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("/poll/poll");
        model.addObject("poll", poll);
        // model.addObject("roles", getRolesAsMap());
        return model;
    }

    private Map<String, String> getRolesAsMap() {
        Map<String, String> roleMap = new HashMap<String, String>();
        Role[] roles = Role.values();
        for (Role role : roles) {
            roleMap.put(role.name(), messageSource.getMessage(role.name(), null, null));
        }
        return roleMap;
    }
}
