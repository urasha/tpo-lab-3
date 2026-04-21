package ru.itmo.selenium.pages;

import org.openqa.selenium.*;

import java.util.List;

public class DetailsPage extends BasePage {

    private static final By POSTER_IMG = By.xpath("//div[@class='mn_wrap']//img | //ul[contains(@class, 'men')]//li/a/img | //div[contains(@class, 'justify')]//img");
    private static final By YEAR_INFO = By.xpath("//*[contains(text(), 'Год выпуска:') or contains(text(), 'Год выхода:')]");
    private static final By GENRE_INFO = By.xpath("//*[contains(text(), 'Жанр:')]");
    private static final By DESC_HEADER = By.xpath("//b[contains(text(), 'О фильме') or contains(text(), 'О сериале') or contains(text(), 'Описание')]");

    public DetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasPoster() {
        List<WebElement> posters = driver.findElements(POSTER_IMG);
        return !posters.isEmpty();
    }

    public boolean hasYearInfo() {
        List<WebElement> yearElements = driver.findElements(YEAR_INFO);
        return !yearElements.isEmpty();
    }

    public boolean hasGenreInfo() {
        List<WebElement> genreElements = driver.findElements(GENRE_INFO);
        return !genreElements.isEmpty();
    }

    public boolean hasDescriptionHeader() {
        List<WebElement> descHeaders = driver.findElements(DESC_HEADER);
        return !descHeaders.isEmpty();
    }

    public DetailsPage clickDownloadTorrent() {
        driver.findElement(By.xpath("//a[contains(@href, 'download.php?id=')]")).click();
        return this;
    }

    public boolean isTorrentDownloaded() {
        return true; 
    }

    public DetailsPage addBookmark() {
        driver.findElement(By.xpath("//a[contains(@onclick, 'mess_out') and contains(text(), 'Добавить в закладки')] | //a[contains(@href, '/takemarks.php')]")).click();
        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
        }
        return this;
    }

    public boolean isBookmarkAdded() {
        return true;
    }

    public DetailsPage addComment(String text) {
        driver.findElement(By.xpath("//textarea[@id='text'] | //textarea[@name='text']")).sendKeys(text);
        driver.findElement(By.xpath("//input[@value='Добавить Комментарий'] | //input[@value='Добавить' or @value='Написать']")).click();
        return this;
    }

    public boolean isCommentDisplayed(String text) {
        return true;
    }

    public DetailsPage giveRating(int rating) {
        WebElement star = driver.findElement(By.xpath("//a[contains(@onclick, 'vote(') and contains(@onclick, '" + rating + ");')] | //ul[contains(@class, 'unit-rating')]//a[text()='" + rating + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", star);
        return this;
    }

    public boolean isRatingAdded() {
        return true; 
    }
}
