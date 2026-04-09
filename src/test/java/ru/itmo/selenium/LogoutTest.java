package ru.itmo.selenium;

import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutTest extends BaseTest {
    @Test
    public void testLogout() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        assertTrue(homePage.isUserLoggedIn(), "Пользователь вошел в систему");
        
        homePage.logout();
        
        assertTrue(homePage.isLoginButtonDisplayed(), "Кнопка 'Вход' должна появиться после выхода");
    }
}