package ru.itmo.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProfilePage extends BasePage {

    public static final String PROFILE_URL = "https://kinozal.tv/my.php";
    
    private static final By OLD_PASS = By.xpath("//input[@name='pass']");
    private static final By NEW_PASS = By.xpath("//input[@name='chpass']");
    private static final By CONFIRM_PASS = By.xpath("//input[@name='passagain']");
    private static final By CHANGE_PASS_BTN = By.xpath("//input[@value='Сменить пароль'] | //input[@type='submit' and contains(@value, 'пароль')]");
    private static final By SUCCESS_MSG = By.xpath("//*[contains(text(), 'Ваш пароль был изменен')] | //div[@class='bx1 justify']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public ProfilePage open() {
        driver.get(PROFILE_URL);
        return this;
    }

    public ProfilePage changePassword(String currentPassword, String newPassword, String confirmPassword) {
        WebElement oldPassInput = driver.findElement(OLD_PASS);
        oldPassInput.clear();
        oldPassInput.sendKeys(currentPassword);

        WebElement newPassInput = driver.findElement(NEW_PASS);
        newPassInput.clear();
        newPassInput.sendKeys(newPassword);

        WebElement confirmPassInput = driver.findElement(CONFIRM_PASS);
        confirmPassInput.clear();
        confirmPassInput.sendKeys(confirmPassword);

        driver.findElement(CHANGE_PASS_BTN).click();
        return this;
    }

    public boolean isPasswordChangeSuccessful() {
        return !driver.findElements(SUCCESS_MSG).isEmpty();
    }
}

