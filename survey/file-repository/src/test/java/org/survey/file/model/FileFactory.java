package org.survey.file.model;

import java.util.ArrayList;
import java.util.List;

import org.survey.file.model.File;
import org.survey.repository.EntityFactory;
import org.survey.user.model.User;

import com.google.common.net.MediaType;

public class FileFactory implements EntityFactory<File, Long> {
    private static final String FILE_CONTENT = "fileContent";
    private User user;

    public FileFactory(User user) {
        this.user = user;
    }

    @Override
    public List<File> getEntities(int count) {
        List<File> entities = new ArrayList<File>();
        for (int i = 0; i < count; i++) {
            File file = new File("filename" + i, MediaType.PLAIN_TEXT_UTF_8.toString(), FILE_CONTENT.getBytes());
            file.setSize(Long.valueOf(FILE_CONTENT.length()));
            file.setCreateTime(Long.valueOf(i));
            file.setOwner(user);
            file.setUrl("url" + i);
            entities.add(file);
        }
        return entities;
    }

    @Override
    public File getUpdatedEntity(File entity) {
        File file = new File(entity.getFilename() + "_updated", MediaType.HTML_UTF_8.toString(),
                (entity.getContent() + "updated").getBytes());
        file.setCreateTime(entity.getCreateTime() + 1);
        file.setSize(entity.getSize() + 1);
        file.setOwner(entity.getOwner());
        file.setUrl(file.getUrl() + "_updated");
        return file;
    }
}
