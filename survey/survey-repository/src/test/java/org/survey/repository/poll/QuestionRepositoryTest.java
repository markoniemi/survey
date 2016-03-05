//package org.survey.repository.poll;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.collections.IteratorUtils;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.survey.model.poll.Poll;
//import org.survey.model.poll.PollFactory;
//import org.survey.model.poll.Question;
//import org.survey.model.poll.QuestionComparator;
//import org.survey.model.poll.QuestionTestFactory;
//import org.survey.model.user.User;
//import org.survey.model.user.UserFactory;
//import org.survey.repository.CrudRepositoryTest;
//import org.survey.repository.user.UserRepository;
//
///**
// * Test UserRepository. Must use SpringJUnit4ClassRunner to enable spring
// * injection. Loaded Spring configuration is defined by ContextConfiguration.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-config-test-stub.xml")
//public class QuestionRepositoryTest extends CrudRepositoryTest<Question, Long> {
//    @Resource
//    protected QuestionRepository questionRepository;
//    @Resource
//    protected PollRepository pollRepository;
//    @Resource
//    protected UserRepository userRepository;
//    private User user;
//    private Poll poll;
//
//    public QuestionRepository getEntityRepository() {
//        return questionRepository;
//    }
//
//    @Before
//    public void setUp() {
//        UserFactory userFactory = new UserFactory();
//        user = userFactory.getEntities(1).get(0);
//        user = userRepository.save(user);
//        PollFactory pollFactory = new PollFactory(user);
//        poll = pollFactory.getEntities(1).get(0);
//        poll = pollRepository.save(poll);
//        entityFactory = new QuestionTestFactory(poll);
//        entityComparator = new QuestionComparator();
//    }
//    
//    @After
//    public void tearDown() {
//        pollRepository.deleteAll();
//        questionRepository.deleteAll();
//        userRepository.deleteAll();
//    }
//
////    @Test
////    public void findByName() {
////        save();
////        Question originalQuestion = orginalEntities.get(0);
////        Question question = (questionRepository.findByName(
////                originalQuestion.getName()));
////        Assert.assertEquals(0, entityComparator.compare(originalQuestion, question));
////    }
//    
//    @Test
//    public void findAllByPoll() {
//        save();
//        Question originalQuestion = orginalEntities.get(0);
//        @SuppressWarnings("unchecked")
//        List<Question> questions = IteratorUtils.toList(questionRepository.findAllByPoll(originalQuestion.getPoll())
//                .iterator());
//        Assert.assertEquals(ENTITY_COUNT, questions.size());
//        Assert.assertTrue(questions.containsAll(orginalEntities));
//        int index = questions.indexOf(originalQuestion);
//        Assert.assertEquals(0, entityComparator.compare(originalQuestion, questions.get(index)));
//    }
//}
