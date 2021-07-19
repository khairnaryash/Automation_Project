package com.automation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WikiHomePage extends BasePage {
    public WikiHomePage(WebDriver m_driver) {
        super(m_driver);
    }

    @Override
    public boolean isDisplayed() {
        return getTitle().equals("Wikipedia, the free encyclopedia");
    }

    public void enterTextInSearch(String text) {
        String logicalName = "wiki_search_box";
        WebElement searchBox = getElementInPage(Element.wiki_search_box);
        sendKeys(searchBox, text);
    }

    public void clickSearch() {
        clickElement(getElementInPage(Element.wiki_search_button));
    }

    private static class Element {
        static By wiki_search_box = By.xpath("//input[@name=\"search\"]");
        static By wiki_search_button = By.xpath("//input[@name=\"go\"]");
    }


}
