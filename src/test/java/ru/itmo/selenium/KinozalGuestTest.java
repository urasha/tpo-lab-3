package ru.itmo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.UUID;

public class KinozalGuestTest extends BaseTest {

    @Test
    public void testMovieDetails_UC02() {
        driver.get("https://kinozal.tv/");
        
        // Кликаем по конкретному фильму по тексту ссылки (Драники)
        WebElement movieLink = driver.findElement(By.xpath("//a[contains(text(),'Драники')]"));
        movieLink.click();
        
        // Проверяем, что оказались на странице раздачи (там должен быть блок с постером или название)
        WebElement poster = driver.findElement(By.xpath("//div[@class='mn_wrap']//img"));
        Assertions.assertTrue(poster.isDisplayed(), "Детали раздачи не открылись (постер не найден)");
    }

    @Test
    public void testRegistrationValidation_UC03() {
        driver.get("https://kinozal.tv/");
        
        // Переход на страницу регистрации
        WebElement registerLink = driver.findElement(By.xpath("//a[contains(@href, '/signup.php')]"));
        registerLink.click();
        
        // Заполняем данные
        String uniqueUser = "bebra" + System.currentTimeMillis();
        
        driver.findElement(By.xpath("//input[@name='wusername']")).sendKeys(uniqueUser);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("vlad.nesterov.2005@mail.ru"); // специально дубль почты
        driver.findElement(By.xpath("//input[@name='wpassword3']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@name='passagain']")).sendKeys("12345678");
        
        // Выбор страны
        WebElement countrySelect = driver.findElement(By.xpath("//select[@name='country']"));
        new Select(countrySelect).selectByVisibleText("Египет");
        
        // Выбор даты
        new Select(driver.findElement(By.xpath("//select[@name='bday_day']"))).selectByVisibleText("12");
        new Select(driver.findElement(By.xpath("//select[@name='bday_month']"))).selectByVisibleText("июля");
        new Select(driver.findElement(By.xpath("//select[@name='bday_year']"))).selectByVisibleText("1974");
        
        // Нажимаем продолжить (Первый шаг) - тут кнопка <input type="submit" ... class="buttonS">
        driver.findElement(By.xpath("//input[@type='submit' and contains(@value, 'Продолжить')] | //form[@id='upt']//input[@type='submit']")).click();
        
        // Поскольку капчу не пройти, ждем, что сайт даст предупреждение об уже занятом email (или просто проверяем, что мы уперлись в ошибку/капчу)
        // В реальном тесте либо alert (из-за пустого поля соглашения), либо текст ошибки
        try {
            WebElement errorMsg = driver.findElement(By.xpath("//*[contains(text(), 'уже существует')] | //*[contains(text(), 'Ошибка')]"));
            Assertions.assertTrue(errorMsg.isDisplayed(), "Текст ошибки не появился");
        } catch (Exception e) {
            // Если вылез alert браузера о недозаполненных полях
            String alertText = driver.switchTo().alert().getText();
            Assertions.assertNotNull(alertText);
            driver.switchTo().alert().accept();
        }
    }

    @Test
    public void testLogin_UC04() {
        driver.get("https://kinozal.tv/");
        
        WebElement loginInput = driver.findElement(By.xpath("//div[@id='main']//form//input[@name='username']"));
        loginInput.sendKeys("bebra123");
        
        WebElement passInput = driver.findElement(By.xpath("//div[@id='main']//form//input[@name='password']"));
        passInput.sendKeys("qwerty");
        
        WebElement loginBtn = driver.findElement(By.xpath("//input[@value='Вход']"));
        loginBtn.click();
        
        // Проверяем, что в блоке пользователя появился пункт меню "Выход" или логин
        WebElement logoutLink = driver.findElement(By.xpath("//a[contains(@href, '/logout.php')]"));
        Assertions.assertTrue(logoutLink.isDisplayed(), "Авторизация не удалась, кнопка выхода не найдена");
    }
}
