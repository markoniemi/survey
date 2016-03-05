package org.survey.selenium;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FailedTestWatcher extends TestWatcher {
    @Setter
    private String message;

    public FailedTestWatcher() {
        
    }
    public FailedTestWatcher(String message) {
        this.message = message;
    }

    @Override
    protected void starting(Description description) {
        log.debug("starting {}", message);
    }


    @Override
    protected void succeeded(Description description) {
        log.debug("succeeded {}", message);
    }

    @Override
    protected void failed(Throwable e, Description description) {
        log.debug("failed {}", message);
    }

    @Override
    protected void finished(Description description) {
        log.debug("finished {}", message);
    }
}
