package com.automation.pageObjects.github;

import com.automation.pageObjects.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoriesPage extends BasePage {

    public UserRepositoriesPage(WebDriver m_driver) {
        super(m_driver);
    }

    public boolean isDisplayed() {
        return true;
    }

    public boolean validateUserName(String userName) {
        String name = getText(Element.username_header);
        return name.equals(userName);
    }

    public List<String> getUserPublicRepoList() {

        List<WebElement> eList = getElementsInPage(Element.user_public_repo_names);
        List<String> repoList = new ArrayList<>();

        for (WebElement e : eList)
            repoList.add(getText(e));

        return repoList;

    }


    private static class Element {
        static By username_header = By.xpath("//a[@class=\"Header-link\"]");
        static By user_public_repo_names = By.xpath("//div[@id=\"org-repositories\"]//li[contains(@class,'public')]//a[@itemprop=\"name codeRepository\"]");
    }


}
