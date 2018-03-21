package org.survey.controller.poll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.survey.model.poll.Poll;
import org.survey.model.poll.Question;
import org.survey.model.user.Role;
import org.survey.service.poll.PollService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PollController {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    // @Qualifier("pollServiceBean")
    private PollService pollService;

    @RequestMapping(value = "/poll/save", method = RequestMethod.POST)
    public String savePoll(@ModelAttribute Poll poll) {
        log.debug("savePoll - saving poll:" + poll);
        if (poll.getId() != null) {
            pollService.update(poll);
        } else {
            pollService.create(poll);
        }
        return "redirect:/poll/polls";
    }

    @RequestMapping(value = "/poll/new", method = RequestMethod.GET)
    public ModelAndView newPoll() {
        return editPoll(null);
    }

    @RequestMapping(value = "/poll/{name}", method = RequestMethod.GET)
    public ModelAndView editPoll(@PathVariable String name) {
        Poll poll = pollService.findOne(name);
        log.debug("editPoll() - found poll: " + poll);
        if (poll == null) {
            poll = new Poll();
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("/poll/poll");
        model.addObject("poll", poll);
        return model;
    }

    @RequestMapping(value = "/poll/delete/{name}", method = RequestMethod.POST)
    public String deletePoll(@PathVariable String name) {
        pollService.delete(name);
        return "redirect:/poll/polls";
    }
    // TODO use GET
    @RequestMapping(value = "/poll/addQuestion", method = RequestMethod.POST)
    public ModelAndView addQuestion(@ModelAttribute Poll poll) {
//        Poll poll = pollService.findOne(name);
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
//        model.addObject("roles", getRolesAsMap());
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
