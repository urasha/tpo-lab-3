package ru.itmo.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage extends BasePage {

    private static final By USERNAME_INPUT = By.xpath("//input[@name='wusername']");
    private static final By EMAIL_INPUT = By.xpath("//input[@name='email']");
    private static final By PASS_INPUT = By.xpath("//input[@name='wpassword3']");
    private static final By PASS_CONFIRM_INPUT = By.xpath("//input[@name='passagain']");
    private static final By COUNTRY_SELECT = By.xpath("//select[@name='country']");
    private static final By BDAY_DAY = By.xpath("//select[@name='bday_day']");
    private static final By BDAY_MONTH = By.xpath("//select[@name='bday_month']");
    private static final By BDAY_YEAR = By.xpath("//select[@name='bday_year']");
    private static final By SUBMIT_BTN = By.xpath("//input[@type='submit' and (contains(@value, 'Продолжить') or contains(@value, 'Завершить'))] | //form[@id='upt']//input[@type='submit']");
    private static final By SUCCESS_MSG = By.xpath("//*[contains(text(), 'осталось совсем немного')] | //input[@name='answer'] | //img[@src='pic.php']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage fillRegistrationForm(String username, String email, String password, String country, String day, String month, String year) throws InterruptedException {
        driver.findElement(USERNAME_INPUT).sendKeys(username);
        Thread.sleep(1000);

        driver.findElement(EMAIL_INPUT).sendKeys(email);
        Thread.sleep(1200);

        driver.findElement(PASS_INPUT).sendKeys(password);
        Thread.sleep(800);

        driver.findElement(PASS_CONFIRM_INPUT).sendKeys(password);
        Thread.sleep(1500);

        new Select(driver.findElement(COUNTRY_SELECT)).selectByVisibleText(country);
        Thread.sleep(1000);

        new Select(driver.findElement(BDAY_DAY)).selectByVisibleText(day);
        Thread.sleep(500);

        new Select(driver.findElement(BDAY_MONTH)).selectByVisibleText(month);
        Thread.sleep(500);

        new Select(driver.findElement(BDAY_YEAR)).selectByVisibleText(year);
        Thread.sleep(1000);

        return this;
    }

    public RegisterPage submitFirstStep() {
        driver.findElement(SUBMIT_BTN).click();
        return this;
    }

    public boolean isFirstStepSuccessful() {
        return !driver.findElements(SUCCESS_MSG).isEmpty();
    }
}

