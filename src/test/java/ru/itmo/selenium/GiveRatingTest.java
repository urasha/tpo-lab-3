package ru.itmo.selenium;

import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.HomePage;
import ru.itmo.selenium.pages.DetailsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GiveRatingTest extends BaseTest {
    @Test
    public void testGiveRating() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        assertTrue(homePage.isUserLoggedIn(), "Пользователь вошел в систему");
        
        DetailsPage detailsPage = homePage.clickFirstSearchResult();
        detailsPage.giveRating(10);
        assertTrue(detailsPage.isRatingAdded(10), "Сообщение об успешной оценке или о том, что оценка уже выставлена, не появилось на странице");
    }
}