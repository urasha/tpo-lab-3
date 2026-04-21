package ru.itmo.selenium.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTextByXPathTextNode(String xpath) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "var result = document.evaluate(arguments[0], document, null, XPathResult.STRING_TYPE, null); return result.stringValue;";
        return (String) js.executeScript(script, xpath);
    }
}
