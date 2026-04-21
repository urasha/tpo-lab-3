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
        
        DetailsPage detailsPage = homePage.openFirstMovie();

        // TODO: проверять конкретные данные (название, год, жанр) после получения доступа к тестовой раздаче
        Assertions.assertTrue(detailsPage.hasPoster(), "Детали раздачи не открылись (постер не найден)");
        Assertions.assertTrue(detailsPage.hasYearInfo(), "Не найдена информация о годе выпуска");
        Assertions.assertTrue(detailsPage.hasGenreInfo(), "Не найдена информация о жанре");
    }
}
