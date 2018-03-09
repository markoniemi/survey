package org.survey.controller.poll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.survey.service.poll.PollService;

import lombok.Getter;
import lombok.Setter;

@Controller
public class PollsController {
    @Getter
    @Setter
    // @Resource
    @Autowired
    private PollService pollService;

    @RequestMapping(value = "/poll/polls", method = RequestMethod.GET)
    public ModelAndView polls() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/poll/polls");
        model.addObject("polls", pollService.findAll());
        return model;
    }

}
