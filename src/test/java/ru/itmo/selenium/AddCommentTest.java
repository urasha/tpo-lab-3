package ru.itmo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import ru.itmo.selenium.pages.DetailsPage;
import ru.itmo.selenium.pages.HomePage;

public class AddCommentTest extends BaseTest {

    @Test
    public void testAddCommentNewUserError() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        Assertions.assertTrue(homePage.isUserLoggedIn(), "Пользователь вошел в систему");
        
        DetailsPage detailsPage = homePage.clickFirstSearchResult();
        String commentText = "Test comment " + System.currentTimeMillis();
        
        detailsPage.addComment(commentText);
        Assertions.assertTrue(detailsPage.isCommentErrorDisplayed(), 
            "Должна появиться ошибка о том, что новичкам запрещено оставлять комментарии первые 3 дня");
    }

    @Test
    public void testAddCommentTooShort() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        Assertions.assertTrue(homePage.isUserLoggedIn(), "Пользователь вошел в систему");
        
        DetailsPage detailsPage = homePage.clickFirstSearchResult();
        detailsPage.addComment("Short");
        
        boolean alertShown;
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            alertShown = alertText.contains("Комментарий не может быть меньше 10 символов");
        } catch (NoAlertPresentException e) {
            alertShown = driver.getPageSource().contains("меньше 10 символов");
        }
        
        Assertions.assertTrue(alertShown, "Должно появиться предупреждение об ошибке: длина меньше 10 символов!");
    }
}

