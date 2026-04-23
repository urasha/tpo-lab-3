package ru.itmo.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForumPage extends BasePage {

    public static final String FORUM_URL = "https://forum.kinozal.tv/";

    public ForumPage(WebDriver driver) {
        super(driver);
    }

    public ForumPage open() {
        driver.get(FORUM_URL);
        return this;
    }

    public ForumCategoryPage openCategory(String categoryName) {
        By categoryLocator = By.xpath("//td[contains(@class, 'alt1Active')]//a[.//text()[contains(., '" + categoryName + "')]]");
        driver.findElement(categoryLocator).click();
        return new ForumCategoryPage(driver);
    }
}
