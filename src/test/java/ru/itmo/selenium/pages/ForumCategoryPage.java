package ru.itmo.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForumCategoryPage extends BasePage {

    private static final By BREADCRUMB_TITLE = By.xpath("//td[contains(@class, 'navbar')]//strong");

    public ForumCategoryPage(WebDriver driver) {
        super(driver);
    }

    public String getActiveCategoryTitle() {
        return driver.findElement(BREADCRUMB_TITLE).getText().trim();
    }
}
