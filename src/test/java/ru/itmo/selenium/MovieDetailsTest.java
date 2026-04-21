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
        
        // Получаем текст ссылки раздачи с главной страницы до перехода
        // По формату сайта там обычно "Название / Оригинал / Год / Жанр / Качество"
        String homePageMovieText = homePage.getFirstMovieFullText();
        String expectedTitleChunk = homePageMovieText.split("/")[0].trim();
        String expectedYearChunk = "";
        if (homePageMovieText.contains("/")) {
            expectedYearChunk = homePageMovieText.split("/")[homePageMovieText.split("/").length > 2 ? 2 : 1].trim(); 
            // Год часто стоит после второго слеша или после первого. Будем проверять на наличие хотя бы кусочка даты
        }
        
        DetailsPage detailsPage = homePage.openFirstMovie();

        // Проверяем, что открылась именно тестовая раздача (совпадает ли базовое название)
        String actualTitle = detailsPage.getTitle();
        Assertions.assertTrue(actualTitle.contains(expectedTitleChunk), 
            String.format("Открылась не та раздача! Ожидали в заголовке '%s', а получили '%s'", expectedTitleChunk, actualTitle));
        
        Assertions.assertTrue(detailsPage.hasPoster(), "Детали раздачи не открылись (постер не найден)");
        
        // Проверяем, что год совпадает с заявленным на главной странице
        String yearInfo = detailsPage.getYearInfo();
        Assertions.assertFalse(yearInfo.isEmpty(), "Не найдена информация о годе выпуска");
        // Проверяем хотя бы наличие даты (одинарный год "2024" или период "2024-2025" / "2024 - 2025")
        if (expectedYearChunk.matches(".*\\d{4}.*")) {
             // Регулярка теперь ищет как одиночный год, так и форматы типа 2024-2025
             String yearRegex = "\\d{4}(?:\\s*-\\s*\\d{4})?";
             java.util.regex.Matcher m = java.util.regex.Pattern.compile(yearRegex).matcher(expectedYearChunk);
             if (m.find()) {
                 String expectedYear = m.group().replace(" ", ""); // Убираем возможные пробелы вокруг '-'
                 String actualYearInfo = yearInfo.replace(" ", ""); // Аналогично в тексте страницы для надежности
                 Assertions.assertTrue(actualYearInfo.contains(expectedYear), 
                     String.format("Год на детальной странице ('%s') не совпадает с тем, что на главной ('%s')", yearInfo, m.group()));
             }
        }
        
        // Жанр проверяем просто на наличие инфо, так как парсить его с главной вслепую сложно
        String genreInfo = detailsPage.getGenreInfo();
        Assertions.assertFalse(genreInfo.isEmpty(), "Не найдена информация о жанре");
        Assertions.assertTrue(genreInfo.length() > 2, "Жанр слишком короткий или не найден");
    }
}
