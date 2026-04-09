package ru.itmo.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SearchPage extends BasePage {

    private static final By CATEGORY_SELECT = By.xpath("//select[@name='w']");
    private static final By YEAR_SELECT = By.xpath("//select[@name='d']");
    private static final By FORMAT_SELECT = By.xpath("//select[@name='f']");
    private static final By FILTER_SUBMIT_BTN = By.xpath("//select[@name='f']/ancestor::form//input[@type='submit' or @type='button']");
    private static final By EMPTY_SEARCH_MSG = By.xpath("//*[contains(text(), 'РќРµС‚ Р°РєС‚РёРІРЅС‹С… СЂР°Р·РґР°С‡, РїСЂРёРЅРѕСЃРёРј РёР·РІРёРЅРµРЅРёСЏ')]");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public SearchPage selectCategory(String category) {
        new Select(driver.findElement(CATEGORY_SELECT)).selectByVisibleText(category);
        return this;
    }

    public SearchPage selectYear(String year) {
        new Select(driver.findElement(YEAR_SELECT)).selectByVisibleText(year);
        return this;
    }

    public SearchPage selectFormat(String format) {
        new Select(driver.findElement(FORMAT_SELECT)).selectByVisibleText(format);
        return this;
    }

    public SearchPage applyFilters() {
        driver.findElement(FILTER_SUBMIT_BTN).click();
        return this;
    }

    public boolean hasMovieInResults(String title) {
        List<WebElement> targetMovieLinks = driver.findElements(By.xpath("//a[contains(text(), '" + title + "')]"));
        return !targetMovieLinks.isEmpty();
    }

    public boolean isSearchEmptyMessageDisplayed() {
        List<WebElement> emptyResultMessages = driver.findElements(EMPTY_SEARCH_MSG);
        return !emptyResultMessages.isEmpty();
    }
}
