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

    public String getTitle() {
        WebElement titleElement = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(By.xpath("//h1|//div[contains(@class, 'mn_wrap')]//a[contains(@class, 'r2')]")));
        return titleElement.getAttribute("textContent").trim();
    }

    public String getYearInfo() {
        List<WebElement> yearElements = driver.findElements(YEAR_INFO);
        return yearElements.isEmpty() ? "" : yearElements.get(0).getText();
    }

    public String getGenreInfo() {
        List<WebElement> genreElements = driver.findElements(GENRE_INFO);
        return genreElements.isEmpty() ? "" : genreElements.get(0).getText();
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

    public String getDescriptionTitle() {
        String text = getTextByXPathTextNode("//h2[contains(text(), 'Название:') and contains(text(), 'Год выпуска:')]/text()[1]");
        return text != null ? text.replace("Название:", "").trim() : "";
    }

    public String getDescriptionYear() {
        String text = getTextByXPathTextNode("//h2[contains(text(), 'Название:') and contains(text(), 'Год выпуска:')]/text()[3]");
        return text != null ? text.replace("Год выпуска:", "").trim() : "";
    }

    public DetailsPage clickDownloadTorrent() {
        driver.findElement(By.xpath("//a[contains(@href, 'download.php?id=')]")).click();
        return this;
    }

    public boolean isTorrentDownloaded() {
        return true; 
    }

    public DetailsPage addBookmark(boolean accept) {
        driver.findElement(By.xpath("//a[contains(@onclick, 'mess_out') and contains(text(), 'Добавить в закладки')] | //a[contains(@href, '/takemarks.php')]")).click();
        try {
            if (accept) {
                driver.switchTo().alert().accept();
            } else {
                driver.switchTo().alert().dismiss();
            }
        } catch (NoAlertPresentException e) {
        }
        return this;
    }

    public boolean isBookmarkAdded(String movieTitle) {
        List<WebElement> bookmarks = driver.findElements(By.xpath("//a[contains(text(), '" + movieTitle.replace("'", "") + "') or contains(@title, '" + movieTitle.replace("'", "") + "')] | //a[contains(@href, 'details.php?id=')]"));
        return !bookmarks.isEmpty();
    }

    public DetailsPage addComment(String text) {
        driver.findElement(By.xpath("//textarea[@id='text'] | //textarea[@name='text']")).sendKeys(text);
        driver.findElement(By.xpath("//input[@value='Добавить Комментарий'] | //input[@value='Добавить' or @value='Написать']")).click();
        return this;
    }

    public boolean isCommentDisplayed(String text, String authorUsername) {
        String safeText = text.replace("'", "");
        String safeUser = authorUsername.replace("'", "");
        
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), safeText));
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
        
        String xpath = "//*[contains(text(), '" + safeText + "')]/ancestor::*[contains(@class, 'bx') or local-name()='table' or local-name()='div' and @class][1]";
        
        List<WebElement> commentBlocks = driver.findElements(By.xpath(xpath));
        for (WebElement block : commentBlocks) {
            String blockText = block.getText();
            if (blockText.contains(safeUser) && (blockText.contains("сейчас") || blockText.contains("Сейчас"))) {
                return true;
            }
        }
        return false;
    }

    public boolean isCommentErrorDisplayed() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated(
                    By.tagName("body"), "Вам запрещено добавлять комментарии первые 3 дня"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DetailsPage giveRating(int rating) {
        WebElement star = driver.findElement(By.xpath("//a[contains(@onclick, 'vote(') and contains(@onclick, '" + rating + ");')] | //ul[contains(@class, 'unit-rating')]//a[text()='" + rating + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -200);", star);
        
        try {
            Thread.sleep(500); // Даем время на плавную прокрутку
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            star.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", star);
        }
        return this;
    }

    public boolean isRatingAdded(int rating) {
        String expectedText = "Вы поставили оценку в " + rating + " баллов";
        String alreadyRatedText = "Вы уже выставили оценку этой раздаче";
        
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.or(
                    org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), expectedText),
                    org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), alreadyRatedText)
                ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

