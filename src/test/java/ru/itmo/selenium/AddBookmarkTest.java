package ru.itmo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.DetailsPage;
import ru.itmo.selenium.pages.HomePage;

public class AddBookmarkTest extends BaseTest {

    @Test
    public void testAddBookmark() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        Assertions.assertTrue(homePage.isUserLoggedIn(), "Пользователь вошел в систему");
        
        DetailsPage detailsPage = homePage.clickFirstSearchResult();
        String movieTitle = detailsPage.getTitle();
        detailsPage.addBookmark(true);
        
        Assertions.assertTrue(detailsPage.isBookmarkAdded(movieTitle), "Фильм должен быть добавлен в закладки");
    }

    @Test
    public void testAddBookmarkCancel() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        Assertions.assertTrue(homePage.isUserLoggedIn(), "Пользователь вошел в систему");
        
        DetailsPage detailsPage = homePage.clickFirstSearchResult();
        detailsPage.addBookmark(false);
        Assertions.assertTrue(driver.getCurrentUrl().contains("details.php"), "Мы должны остаться на странице раздачи после отмены");
    }
}

