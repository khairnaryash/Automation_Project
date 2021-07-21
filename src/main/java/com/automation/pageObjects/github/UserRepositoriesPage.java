package com.automation.pageObjects.github;

import com.automation.dataObject.UserRepoDetails;
import com.automation.pageObjects.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
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
        return name.equalsIgnoreCase(userName);
    }

    public List<UserRepoDetails> getUserPublicRepoListAndDescription() {

        int count = getElementsInPage(Element.user_public_repo_names).size();
        if (count < 1)
            return null;

        List<UserRepoDetails> repoList = new ArrayList<>();
        String nameXpath = "(//div[@id=\"org-repositories\"]//li[contains(@class,'public')]//a[@itemprop=\"name codeRepository\"])[%d]";
        String descriptionXpath = "(//div[@id=\"org-repositories\"]//li[contains(@class,'public')]//a[@itemprop=\"name codeRepository\"])[%d]/ancestor::div[@data-view-component=\"true\"]//p[@itemprop=\"description\"]";
        for (int i = 1; i <= count; i++) {
            String name = getText(By.xpath(String.format(nameXpath, i)));
            By by = By.xpath(String.format(descriptionXpath, i));
            String description = "";
            if (checkIfElementPresent(by))
                description = getText(by);
            UserRepoDetails userRepoDetails = new UserRepoDetails();
            userRepoDetails.setName(name);
            userRepoDetails.setDescription(description);
            repoList.add(userRepoDetails);
        }
        return repoList;

    }


    private static class Element {
        static By username_header = By.xpath("//header[contains(@class,'pagehead')]//h1");
        static By user_public_repo_names = By.xpath("//div[@id=\"org-repositories\"]//li[contains(@class,'public')]//a[@itemprop=\"name codeRepository\"]");
    }


}
