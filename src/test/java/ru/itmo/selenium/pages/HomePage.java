package ru.itmo.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    public static final String HOME_URL = "https://kinozal.tv/";
    
    private static final By SEARCH_INPUT = By.xpath("//input[@name='s']");
    private static final By SEARCH_BUTTON = By.xpath("//form[contains(@action, 'browse.php')]//input[@type='submit' or contains(@class, 'submit')]");
    private static final By FIRST_MOVIE_LINK = By.xpath("(//div[contains(@class, 'tp1_title')]/a[contains(@href, '/details.php?id=')] | //td[contains(@class, 'nam')]/a[contains(@href, '/details.php?id=')])[1]");
    
    private static final By LOGIN_INPUT = By.xpath("//div[@id='main']//form//input[@name='username']");
    private static final By PASS_INPUT = By.xpath("//div[@id='main']//form//input[@name='password']");
    private static final By LOGIN_BTN = By.xpath("//input[@value='Вход']");
    private static final By LOGOUT_LINK = By.xpath("//a[contains(@href, '/logout.php')]");
    private static final By REGISTER_LINK = By.xpath("//a[contains(@href, '/signup.php')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(HOME_URL);
        return this;
    }

    public HomePage login(String user, String pass) {
        driver.findElement(LOGIN_INPUT).sendKeys(user);
        driver.findElement(PASS_INPUT).sendKeys(pass);
        driver.findElement(LOGIN_BTN).click();
        return this;
    }

    public boolean isUserLoggedIn() {
        return !driver.findElements(LOGOUT_LINK).isEmpty() && driver.findElement(LOGOUT_LINK).isDisplayed();
    }

    public HomePage logout() {
        driver.findElement(LOGOUT_LINK).click();
        driver.switchTo().alert().accept();
        return this;
    }

    public DetailsPage clickFirstSearchResult() {
        String url = driver.findElement(FIRST_MOVIE_LINK).getAttribute("href");
        driver.get(url);
        return new DetailsPage(driver);
    }

    public boolean isLoginButtonDisplayed() {
        return !driver.findElements(LOGIN_BTN).isEmpty();
    }

    public RegisterPage clickRegister() {
        driver.findElement(REGISTER_LINK).click();
        return new RegisterPage(driver);
    }

    public SearchPage search(String query) {
        WebElement searchInput = driver.findElement(SEARCH_INPUT);
        searchInput.clear();
        searchInput.sendKeys(query);
        
        driver.findElement(SEARCH_BUTTON).click();
        return new SearchPage(driver);
    }

    public String getFirstMovieFullText() {
        return driver.findElement(FIRST_MOVIE_LINK).getText();
    }

    public DetailsPage openFirstMovie() {
        String url = driver.findElement(FIRST_MOVIE_LINK).getAttribute("href");
        driver.get(url);
        return new DetailsPage(driver);
    }

    public String getFirstMovieTitleFromDesc() {
        String text = getTextByXPathTextNode("(//div[contains(., 'Название:') and contains(., 'Год выпуска:')])[1]/text()[1]");
        return text != null ? text.replace("Название:", "").trim() : "";
    }

    public String getFirstMovieYearFromDesc() {
        String text = getTextByXPathTextNode("(//div[contains(., 'Название:') and contains(., 'Год выпуска:')])[1]/text()[3]");
        return text != null ? text.replace("Год выпуска:", "").trim() : "";
    }
}
