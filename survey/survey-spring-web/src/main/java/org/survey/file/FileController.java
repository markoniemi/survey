package org.survey.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.survey.model.file.File;
import org.survey.service.file.FileService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class FileController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/file/save", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        fileService.create(createFile(file, file.getOriginalFilename(), bytes));
        return "redirect:/file/files";
    }

    @RequestMapping(value = "/file/new", method = RequestMethod.GET)
    public ModelAndView newFile() {
        File file = new File();
        ModelAndView model = new ModelAndView();
        model.setViewName("file/file");
        model.addObject("file", file);
        return model;
    }

    @RequestMapping(value = "/file/delete/{id}", method = RequestMethod.POST)
    public String deleteFile(@PathVariable Long id) {
        fileService.delete(id);
        return "redirect:/file/files";
    }

    @RequestMapping(value = "/file/{id}", method = RequestMethod.GET)
    public String downloadFile(@PathVariable Long id, HttpServletResponse response) {
        File file = fileService.findOne(id);
        writeFileToResponse(response, file);
        return "redirect:/file/files";
    }

    private void writeFileToResponse(HttpServletResponse response, File file) {
        if (file == null) {
            return;
        }
        try {
            IOUtils.copy(new ByteArrayInputStream(file.getContent()), response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getFilename());
            response.setContentLength(file.getSize().intValue());
            // for in-browser handling use
            // String mimeType =
            // URLConnection.guessContentTypeFromName(file.getFilename());
            response.setContentType("application/octet-stream");
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
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
        // TODO get owner
        // file.setOwner(getUser());
        file.setCreateTime(System.currentTimeMillis());
        file.setSize(uploadedFile.getSize());
        // TODO change files rest to files/:user/:filename
        file.setUrl("/survery-web/api/rest/files/");
        return file;
    }
}