package org.survey.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.survey.service.user.UserService;

import lombok.Getter;
import lombok.Setter;

@Controller
public class UsersController {
    @Getter
    @Setter
//    @Resource
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/user/users")
    public ModelAndView users() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/user/users");
        model.addObject("users", userService.findAll());
        return model;
    }

}
