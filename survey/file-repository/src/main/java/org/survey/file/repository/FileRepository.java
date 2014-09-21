package org.survey.file.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.survey.file.model.File;
import org.survey.user.model.User;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {
    List<File> findAllByFilename(@Param("filename") String filename);

    List<File> findAllByOwner(@Param("owner") User owner);
    // List<File> deleteAllByUsername(@Param("username") String username);
}
