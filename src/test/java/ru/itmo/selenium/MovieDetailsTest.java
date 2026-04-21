package ru.itmo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.HomePage;
import ru.itmo.selenium.pages.DetailsPage;

public class MovieDetailsTest extends BaseTest {

    @Test
    public void testMovieDetails() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        
        String expectedTitle = homePage.getFirstMovieTitleFromDesc();
        String expectedYear = homePage.getFirstMovieYearFromDesc();
        
        DetailsPage detailsPage = homePage.openFirstMovie();

        String actualTitle = detailsPage.getDescriptionTitle();
        String actualYear = detailsPage.getDescriptionYear();
        
        Assertions.assertEquals(expectedTitle, actualTitle, "Название на детальной странице не совпадает с главной");
        Assertions.assertEquals(expectedYear, actualYear, "Год на детальной странице не совпадает с главной");
    }
}
