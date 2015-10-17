package org.survey.service.file;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;

import lombok.extern.log4j.Log4j2;

import org.survey.model.file.File;
import org.survey.repository.file.FileRepository;

import com.google.common.collect.Iterables;

@WebService(endpointInterface = "org.survey.service.file.FileService", serviceName = "fileService")
@Log4j2
public class FileServiceImpl implements FileService {
    @Resource
    FileRepository fileRepository;
    private static final File[] EMPTY_FILE_ARRAY = new File[0];

    @Override
    public File[] findAll() {
        Iterable<File> files = fileRepository.findAll();
        // return empty list instead of null
        if (Iterables.isEmpty(files)) {
            return EMPTY_FILE_ARRAY;
        } else {
            return Iterables.toArray(files, File.class);
        }
    }

    @Override
    public File create(@WebParam(name = "file") File file) {
        log.debug(file);
        return fileRepository.save(file);
    }

    @Override
    public File update(@WebParam(name = "file") File file) {
        return fileRepository.save(file);
    }

    @Override
    public File findOne(@WebParam(name = "id") long id) {
        return fileRepository.findOne(id);
    }

    @Override
    public boolean exists(@WebParam(name = "id") long id) {
        return fileRepository.exists(id);
    }

    @Override
    public void delete(@WebParam(name = "id") long id) {
        if (fileRepository.exists(id)) {
            fileRepository.delete(id);
        }
    }

    @Override
    public long count() {
        return fileRepository.count();
    }
}
