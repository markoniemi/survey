package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FilesPage extends AbstractPage {
    public FilesPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickAddFile() {
        click(By.id("addFile"));
        assertTitle("File");
    }

    public void assertFile(String filename) {
        WebElement element = webDriver.findElement(By.xpath("//tr//td//a[contains(text(),'" + filename + "')]"));
        Assert.assertNotNull(element);
    }
}
