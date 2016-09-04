package org.survey.repository.file;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.survey.model.file.File;
import org.survey.model.user.User;

@Repository
public interface FileRepository extends PagingAndSortingRepository<File, Long> {
    List<File> findAllByFilename(@Param("filename") String filename);

    List<File> findAllByOwner(@Param("owner") User owner);
    // List<File> deleteAllByUsername(@Param("username") String username);
}
