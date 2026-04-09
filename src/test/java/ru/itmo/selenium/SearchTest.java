package ru.itmo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.HomePage;
import ru.itmo.selenium.pages.SearchPage;

public class SearchTest extends BaseTest {

    private static final String SEARCH_QUERY = "класс";
    private static final String TARGET_MOVIE_TITLE = "Гинтама: Класс Гимпати";

    @Test
    public void testSearchAndFilter() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        SearchPage searchPage = homePage.search(SEARCH_QUERY)
                .selectCategory("золото")
                .selectYear("2025")
                .selectFormat("Возр.")
                .applyFilters();

        Assertions.assertTrue(searchPage.hasMovieInResults(TARGET_MOVIE_TITLE),
                "Ошибка: В результатах поиска не найдена раздача '" + TARGET_MOVIE_TITLE + "'!");
    }

    @Test
    public void testSearchNotFound() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        SearchPage searchPage = homePage.search("авадакедавра123456789");

        Assertions.assertTrue(searchPage.isSearchEmptyMessageDisplayed(),
                "Ошибка: При неверном поиске ожидаемое сообщение об отсутствии раздач не появилось!");
    }
}
