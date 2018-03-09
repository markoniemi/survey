package org.survey.controller.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.survey.service.file.FileService;

import lombok.Getter;
import lombok.Setter;

@Controller
public class FilesController {
    @Getter
    @Setter
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/file/files", method = RequestMethod.GET)
    public ModelAndView files() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/file/files");
        model.addObject("files", fileService.findAll());
        return model;
    }

}
