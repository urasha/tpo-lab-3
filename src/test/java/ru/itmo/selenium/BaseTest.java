package ru.itmo.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
