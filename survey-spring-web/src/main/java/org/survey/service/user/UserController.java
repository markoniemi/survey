package org.survey.service.user;

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
import org.survey.model.user.Role;
import org.survey.model.user.User;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class UserController {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    // @Qualifier("userServiceBean")
    private UserService userService;

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user) {
        if (user.getId() != null) {
            userService.update(user);
        } else {
            userService.create(user);
        }
        return "redirect:/user/users";
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.GET)
    public ModelAndView newUser() {
        return editUser(null);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable String username) {
        User user = userService.findOne(username);
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

    @RequestMapping(value = "/user/delete/{username}", method = RequestMethod.POST)
    public String deleteUser(@PathVariable String username) {
        userService.delete(username);
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
