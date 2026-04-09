package ru.itmo.selenium;

import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.HomePage;
import ru.itmo.selenium.pages.RegisterPage;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest extends BaseTest {
    @Test
    public void testRegistration() throws InterruptedException {
        HomePage homePage = new HomePage(driver).open();
        RegisterPage registerPage = homePage.clickRegister();

        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        String uniqueUser = "usr" + UUID.randomUUID().toString().substring(0, 8);
        String uniqueEmail = "test.mail." + uniqueSuffix + "@mail.ru";

        registerPage.fillRegistrationForm(uniqueUser, uniqueEmail, "12345678", "Египет", "12", "июля", "1974");

        Thread.sleep(10000);

        registerPage.submitFirstStep();
        assertTrue(registerPage.isFirstStepSuccessful(), "Проверка перехода на этап подтверждения регистрации или второй шаг.");
    }
}