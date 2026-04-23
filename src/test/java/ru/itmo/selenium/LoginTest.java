package ru.itmo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.HomePage;

public class LoginTest extends BaseTest {

    @Test
    public void testLogin() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        
        Assertions.assertTrue(homePage.isUserLoggedIn(), "Пользователь должен быть авторизован");
    }

    @Test
    public void testInvalidLogin() {
        HomePage homePage = new HomePage(driver).open();
        
        String invalidUser = "wrongUser123444";
        homePage.login(invalidUser, "wrongPassword");
        
        String pageSource = driver.getPageSource();
        Assertions.assertTrue(pageSource.contains("Не найдено имя пользователя « %s »".formatted(invalidUser)));
    }
}
