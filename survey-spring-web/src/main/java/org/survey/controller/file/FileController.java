package org.survey.controller.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.survey.model.file.File;
import org.survey.security.SecurityUtil;
import org.survey.service.file.FileService;
import org.survey.service.user.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class FileController {
    @Resource
    private FileService fileService;
    @Resource
    private UserService userService;

    @PostMapping(value = "/file/save")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        fileService.create(createFile(file, file.getOriginalFilename(), bytes));
        return "redirect:/file/files";
    }

    @GetMapping(value = "/file/new")
    public ModelAndView newFile() {
        File file = new File();
        ModelAndView model = new ModelAndView();
        model.setViewName("/file/file");
        model.addObject("file", file);
        return model;
    }

    @PostMapping(value = "/file/delete/{id}")
    public String deleteFile(@PathVariable Long id) {
        fileService.delete(id);
        return "redirect:/file/files";
    }

    @GetMapping(value = "/file/{id}")
    public void downloadFile(@PathVariable Long id, HttpServletResponse response) {
        File file = fileService.findOne(id);
        writeFileToResponse(response, file);
    }

    private void writeFileToResponse(HttpServletResponse response, File file) {
        if (file == null) {
            return;
        }
        try {
            IOUtils.copy(new ByteArrayInputStream(file.getContent()), response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getFilename());
            response.setContentLength(file.getContent().length);
            // for in-browser handling use
            // String mimeType =
            // URLConnection.guessContentTypeFromName(file.getFilename());
            response.setContentType("application/octet-stream");
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException("IOError writing file to output stream", e);
        }
    }

    /**
     * Create a File from Part.
     */
    private File createFile(MultipartFile uploadedFile, String filename, byte[] fileContent) {
        File file = new File();
        file.setFilename(filename);
        file.setMimeType(uploadedFile.getContentType());
        file.setContent(fileContent);
         file.setOwner(userService.findOne(SecurityUtil.getUsername()));
        file.setCreateTime(System.currentTimeMillis());
        // TODO change files rest to files/:user/:filename
        file.setUrl("/survey-web/api/rest/files/");
        return file;
    }
}