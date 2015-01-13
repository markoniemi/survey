package org.survey.bean;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.FacesUtil;
import org.survey.model.poll.Poll;
import org.survey.service.poll.PollService;

@Component
@Scope("request")
public class PollsBean {
    @Getter
    @Setter
    @Autowired
    private PollService pollService;
    @Getter
    @SuppressWarnings("PMD.UnusedPrivateField")
    private List<Poll> polls;

    @PostConstruct
    public void initialize() {
        Poll[] polls = pollService.findAll();
        if (polls != null) {
            this.polls = Arrays.asList(polls);
        }
    }

    /**
     * Called from polls.xhtml when user pressed Delete in pollTable.
     */
    public void deletePoll() {
        pollService.delete(getRequestParameter("name"));
        initialize();
    }

    protected String getRequestParameter(String parameterName) {
        return FacesUtil.getRequestParameter(parameterName);
    }
}
