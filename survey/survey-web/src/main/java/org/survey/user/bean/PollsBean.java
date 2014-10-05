package org.survey.user.bean;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.poll.model.Poll;
import org.survey.poll.service.PollService;
import org.survey.user.FacesUtil;

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
        polls = Arrays.asList(pollService.findAll());
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
