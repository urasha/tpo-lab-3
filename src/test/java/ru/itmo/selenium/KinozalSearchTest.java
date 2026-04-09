package ru.itmo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class KinozalSearchTest extends BaseTest {

    @Test
    public void testSearchAndFilter_UC01() {
        // 1. Открываем главную страницу
        driver.get("https://kinozal.tv/");

        // 2. Вводим текст в основную строку поиска. 
        // Важно: используем только XPath, как требует задание. Никаких By.id.
        WebElement searchInput = driver.findElement(By.xpath("//input[@name='s']"));
        searchInput.sendKeys("класс");

        // 3. Отправляем запрос (жмем кнопку отправки рядом со строкой браузера)
        WebElement mainSearchBtn = driver.findElement(By.xpath("//form[contains(@action, 'browse.php')]//input[@type='submit' or contains(@class, 'submit')]"));
        mainSearchBtn.click();

        // -- На этом этапе мы оказываемся на странице Выдачи/Расширенного поиска --
        
        // 4. Выбираем фильтр "w" (Золото)
        WebElement selectW = driver.findElement(By.xpath("//select[@name='w']"));
        new Select(selectW).selectByVisibleText("золото");

        // 5. Выбираем фильтр "d" (Год = 2025)
        WebElement selectD = driver.findElement(By.xpath("//select[@name='d']"));
        new Select(selectD).selectByVisibleText("2025");

        // 6. Выбираем фильтр "f" (Сортировка: Возр.)
        WebElement selectF = driver.findElement(By.xpath("//select[@name='f']"));
        new Select(selectF).selectByVisibleText("Возр.");

        // 7. ТУТ В SELENIUM IDE БЫЛ ПРОПУСК! 
        // Чтобы фильтры применились, нужно еще раз нажать кнопку поиска.
        WebElement filterSearchBtn = driver.findElement(By.xpath("//select[@name='f']/ancestor::form//input[@type='submit' or @type='button']"));
        filterSearchBtn.click();

        // 8. ПРОВЕРКА (Assert) ОЖИДАЕМОГО РЕЗУЛЬТАТА ИЗ НАШЕГО ОТЧЕТА:
        // Проверяем, что появилась нужная раздача (Основной поток из UC-01)
        // Используем относительный XPath с contains(), чтобы не зависеть от изменения порядка строк в таблице.
        List<WebElement> targetMovieLinks = driver.findElements(By.xpath("//a[contains(text(), 'Гинтама: Класс Гимпати')]"));
        
        Assertions.assertFalse(targetMovieLinks.isEmpty(), 
            "Ошибка: В результатах поиска не найдена раздача 'Гинтама: Класс Гимпати'!");
    }

    @Test
    public void testSearch_NotFound_UC01_Alt() {
        // Альтернативный поток UC-01: Поиск несуществующей раздачи
        driver.get("https://kinozal.tv/");

        // Вводим заведомо несуществующий текст
        WebElement searchInput = driver.findElement(By.xpath("//input[@name='s']"));
        searchInput.sendKeys("авадакедавра123456789");

        WebElement mainSearchBtn = driver.findElement(By.xpath("//form[contains(@action, 'browse.php')]//input[@type='submit' or contains(@class, 'submit')]"));
        mainSearchBtn.click();

        // Проверяем появление текста об ошибке "Нет активных раздач..."
        List<WebElement> emptyResultMessages = driver.findElements(By.xpath("//*[contains(text(), 'Нет активных раздач, приносим извинения')]"));
        
        Assertions.assertFalse(emptyResultMessages.isEmpty(), 
            "Ошибка: При неверном поиске ожидаемое сообщение об отсутствии раздач не появилось!");
    }
}
