package org.survey.file.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.survey.file.model.File;
import org.survey.repository.CrudRepositoryStub;
import org.survey.user.model.User;

public class FileRepositoryStub extends CrudRepositoryStub<File, Long>
        implements FileRepository {

    @Override
    public List<File> findAllByFilename(@Param("filename") String filename) {
        List<File> foundFiles = new ArrayList<File>();
        for (File file : entities) {
            if (file.getFilename().equals(filename)) {
                foundFiles.add(file);
            }
        }
        return foundFiles;
    }

    @Override
    public List<File> findAllByOwner(@Param("owner") User owner) {
        List<File> foundFiles = new ArrayList<File>();
        for (File file : entities) {
            if (file.getOwner().equals(owner)) {
                foundFiles.add(file);
            }
        }
        return foundFiles;
    }
}
