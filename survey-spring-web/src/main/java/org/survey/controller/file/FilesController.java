package org.survey.controller.file;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.survey.service.file.FileService;

@Controller
public class FilesController {
    @Resource
    private FileService fileService;

    @GetMapping(value = "/file/files")
    public ModelAndView files() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/file/files");
        model.addObject("files", fileService.findAll());
        return model;
    }

}
