package ru.itmo.selenium;

import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.HomePage;
import ru.itmo.selenium.pages.DetailsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddCommentTest extends BaseTest {
    @Test
    public void testAddComment() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        assertTrue(homePage.isUserLoggedIn(), "Пользователь вошел в систему");
        
        DetailsPage detailsPage = homePage.clickFirstSearchResult();
        String commentText = "Test comment " + System.currentTimeMillis();
        detailsPage.addComment(commentText);
        
        assertTrue(detailsPage.isCommentDisplayed(commentText), "Комментарий должен отображаться");
    }
}