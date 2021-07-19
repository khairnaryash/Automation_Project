package com.automation.pageObjects.github;

import com.automation.pageObjects.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GitHubHomePage extends BasePage {
    public GitHubHomePage(WebDriver m_driver) {
        super(m_driver);
    }

    @Override
    public boolean isDisplayed() {
        return getTitle().equals("GitHub: Where the world builds software · GitHub");
    }

    public void enterTextInSearch(String text) {
        WebElement searchBox = getElementInPage(Element.gihub_search_box);
        sendKeys(searchBox, text);
        searchBox.submit();
    }


    private static class Element {
        static By gihub_search_box = By.xpath("//input[@name='q']");
    }


}
