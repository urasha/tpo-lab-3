package ru.itmo.selenium;

import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.DetailsPage;
import ru.itmo.selenium.pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DownloadTorrentTest extends BaseTest {
    @Test
    public void testDownloadTorrent() {
        HomePage homePage = new HomePage(driver).open();
        homePage.login(VALID_USERNAME, VALID_PASSWORD);
        assertTrue(homePage.isUserLoggedIn(), "Пользователь вошел в систему");
        
        DetailsPage detailsPage = homePage.clickFirstSearchResult();
        detailsPage.clickDownloadTorrent();
        
        assertTrue(detailsPage.isTorrentDownloaded(), "Торрент-файл должен быть скачан");
    }
}