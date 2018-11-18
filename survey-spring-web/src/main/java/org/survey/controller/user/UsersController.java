package org.survey.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.survey.service.user.UserService;

@Controller
public class UsersController {
    @Resource
    private UserService userService;

    @GetMapping(value = "/user/users")
    public ModelAndView users() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/user/users");
        model.addObject("users", userService.findAll());
        return model;
    }
}
