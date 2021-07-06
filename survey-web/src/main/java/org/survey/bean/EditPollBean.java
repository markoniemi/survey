package org.survey.bean;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.faces.bean.ViewScoped;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.survey.FacesUtil;
import org.survey.model.poll.Poll;
import org.survey.model.poll.Question;
import org.survey.model.poll.QuestionType;
import org.survey.model.user.User;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Component
@ViewScoped
@Log4j2
@SuppressWarnings("PMD.UnusedPrivateField")
public class EditPollBean {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Poll poll;
    @Setter
    @Resource
    PollService pollService;
    @Resource
    private UserService userService;
    @SuppressWarnings("pmd.UnusedPrivateField")
    @Getter
    private QuestionType[] questionTypes = QuestionType.values();

    public String addPoll() {
        poll = new Poll();
        poll.setOwner(getCurrentUser());
        return "editPoll";
    }

    /**
     * Called from polls.xhtml when user pressed Edit in pollTable.
     */
    public String editPoll() {
        id = Long.valueOf(getRequestParameter("id"));
        if (id != null) {
            poll = pollService.findById(id);
        }
        return "editPoll";
    }

    /**
     * Called from editPoll.xhtml page when user presses Add question button.
     */
    public void addQuestion() {
        if (poll.getQuestions() == null) {
            poll.setQuestions(new ArrayList<Question>());
        }
        Question question = new Question();
        question.setType(QuestionType.LABEL);
        question.setPoll(poll);
        poll.getQuestions().add(question);
    }

    /**
     * Called from editPoll.xhtml page when user changes a question type.
     * editPoll.xhtml contains a selectOneMenu with
     * valueChangeListener="#{editPollBean.questionTypeChanged(status.index)}"
     */
    public void questionTypeChanged(int index) {
        log.debug("index: {}", index);
        Question question = poll.getQuestions().get(index);
        log.debug("oldQuestion.type: {}", question.getType());
    }

    /**
     * Called from editPoll.xhtml page when user presses Save button.
     */
    public String savePoll() {
        log.info(poll.toString());
        try {
            if (poll.getId() == null) {
                poll = pollService.create(poll);
            } else {
                poll = pollService.update(poll);
            }
        } catch (IllegalArgumentException e) {
            log.debug("Unable to create poll, a poll with same name already exists: {]", poll.getName());
            showMessage(null, "pollExists", e);
            return null;
        }
        return "pollSaved";
    }

    public void cancel() {
        poll = new Poll();
    }

    protected User getCurrentUser() {
        return userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    protected String getRequestParameter(String parameterName) {
        return FacesUtil.getRequestParameter(parameterName);
    }

    /**
     * showMessage is package private to enable overriding in a test case.
     */
    void showMessage(String id, String messageKey, Exception e) {
        log.error(messageKey, e);
        FacesUtil.showMessage(messageKey);
    }
}
