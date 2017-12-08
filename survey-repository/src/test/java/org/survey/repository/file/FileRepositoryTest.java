package org.survey.repository.file;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.IteratorUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.RepositoryStubTestConfig;
import org.survey.model.file.File;
import org.survey.model.file.FileComparator;
import org.survey.model.file.FileFactory;
import org.survey.model.user.User;
import org.survey.model.user.UserFactory;
import org.survey.repository.CrudRepositoryTest;
import org.survey.repository.user.UserRepository;

/**
 * Test UserRepository. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = RepositoryStubTestConfig.class))
public class FileRepositoryTest extends CrudRepositoryTest<File, Long> {
	@Resource
	protected FileRepository fileRepository;
	@Resource
    protected UserRepository userRepository;
    private User user;

	public FileRepository getEntityRepository() {
		return fileRepository;
	}

	@Before
	public void setUp() {
        UserFactory userFactory = new UserFactory();
        user = userFactory.getEntities(1).get(0);
        user = userRepository.save(user);
		entityFactory = new FileFactory(user);
		entityComparator = new FileComparator();
	}
	
    @After
    public void tearDown() {
        fileRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
	public void findAllByFilename() {
		save();
		File originalFile1 = orginalEntities.get(0);
		@SuppressWarnings("unchecked")
		List<File> files = IteratorUtils.toList(fileRepository.findAllByFilename(
				originalFile1.getFilename()).iterator());
		Assert.assertEquals(1, files.size());
		Assert.assertEquals(0, entityComparator.compare(originalFile1, files.get(0)));
	}

    @Test
    public void findAllByOwner() {
        save();
        File originalFile = orginalEntities.get(0);
        @SuppressWarnings("unchecked")
        List<File> files = IteratorUtils.toList(fileRepository.findAllByOwner(
                originalFile.getOwner()).iterator());
        Assert.assertEquals(ENTITY_COUNT, files.size());
        Assert.assertTrue(files.containsAll(orginalEntities));
        int index = files.indexOf(originalFile);
        Assert.assertEquals(0, entityComparator.compare(originalFile, files.get(index)));
    }
}
