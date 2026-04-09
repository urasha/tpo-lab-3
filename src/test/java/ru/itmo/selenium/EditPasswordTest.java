package ru.itmo.selenium;

import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.HomePage;
import ru.itmo.selenium.pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditPasswordTest extends BaseTest {
    @Test
    public void testEditPassword() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        assertTrue(homePage.isUserLoggedIn(), "Пользователь вошел в систему");
        
        ProfilePage profilePage = new ProfilePage(driver).open();
        
        String newPassword = VALID_PASSWORD + "123";
        try {
            profilePage.changePassword(VALID_PASSWORD, newPassword);
            assertTrue(profilePage.isPasswordChangeSuccessful(), "Пароль должен быть успешно изменен");
        } finally {
            profilePage.changePassword(newPassword, VALID_PASSWORD);
        }
    }
}