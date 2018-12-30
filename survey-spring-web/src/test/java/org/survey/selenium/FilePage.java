package org.survey.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FilePage extends AbstractPage {
    public FilePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void addFile(String filename) {
        setText(By.id("file"), System.getProperty("user.dir") + "\\" + filename);
        click(By.id("submit"));
        assertTitle("Files");
    }
}
