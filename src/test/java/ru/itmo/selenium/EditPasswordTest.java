package ru.itmo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.HomePage;
import ru.itmo.selenium.pages.ProfilePage;

public class EditPasswordTest extends BaseTest {

    @Test
    public void testEditPasswordWrongOld() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        Assertions.assertTrue(homePage.isUserLoggedIn(), "Пользователь должен быть авторизован");
        
        ProfilePage profilePage = new ProfilePage(driver).open();
        
        profilePage.changePassword(VALID_PASSWORD + "wrong", "newPass123", "newPass123");
        
        Assertions.assertTrue(driver.getCurrentUrl().contains("takeprofedit"), "Ожидаем редирект в обработчик");
        Assertions.assertTrue(driver.getPageSource().contains("неверно указали старый"), "Ожидалась ошибка старого пароля");
    }

    @Test
    public void testEditPasswordTooShort() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        Assertions.assertTrue(homePage.isUserLoggedIn(), "Пользователь должен быть авторизован");
        
        ProfilePage profilePage = new ProfilePage(driver).open();
        
        profilePage.changePassword(VALID_PASSWORD, "12345", "12345");
        
        Assertions.assertTrue(driver.getCurrentUrl().contains("takeprofedit"), "Ожидаем редирект в обработчик");
        Assertions.assertTrue(driver.getPageSource().contains("менее 6 символов"), "Ожидалась ошибка длины пароля");
    }

    @Test
    public void testEditPasswordMismatch() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        Assertions.assertTrue(homePage.isUserLoggedIn(), "Пользователь должен быть авторизован");
        
        ProfilePage profilePage = new ProfilePage(driver).open();
        
        profilePage.changePassword(VALID_PASSWORD, "newPass123", "newPass456");
        
        Assertions.assertTrue(driver.getCurrentUrl().contains("takeprofedit"), "Ожидаем редирект в обработчик");
        Assertions.assertTrue(driver.getPageSource().contains("Пароли не совпадают"), "Ожидалась ошибка совпадения паролей");
    }
}

