package org.survey.repository.file;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.survey.model.file.File;
import org.survey.model.user.User;
import org.survey.repository.CrudRepositoryStub;

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
