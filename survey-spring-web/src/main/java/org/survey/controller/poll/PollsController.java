package org.survey.controller.poll;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.survey.service.poll.PollService;

@Controller
public class PollsController {
    @Resource
    private PollService pollService;

    @GetMapping(value = "/poll/polls")
    public ModelAndView polls() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/poll/polls");
        model.addObject("polls", pollService.findAll());
        return model;
    }
}
