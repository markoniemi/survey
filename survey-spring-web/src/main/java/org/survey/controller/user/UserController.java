package org.survey.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.survey.controller.UserValidator;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.service.user.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class UserController {
    @Resource
    private MessageSource messageSource;
    @Resource
    private UserService userService;
    @Resource
    private UserValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @PostMapping(value = "/user/save")
    public String saveUser(@ModelAttribute @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/user";
        }
        try {
            if (user.getId() != null) {
                userService.update(user);
            } else {
                userService.create(user);
            }
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("user", e.getMessage()));
        }
        if (bindingResult.hasErrors()) {
            return "/user/user";
        }
        return "redirect:/user/users";
    }

    @GetMapping(value = "/user/new")
    public ModelAndView newUser() {
        return editUser(null);
    }

    @GetMapping(value = "/user/{username}")
    public ModelAndView editUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        log.debug("editUser() - found user: " + user);
        if (user == null) {
            user = new User();
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("/user/user");
        model.addObject("user", user);
        model.addObject("roles", getRolesAsMap());
        return model;
    }

    @PostMapping(value = "/user/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.delete(userService.findByUsername(username).getId());
        return "redirect:/user/users";
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
