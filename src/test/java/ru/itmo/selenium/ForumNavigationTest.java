package ru.itmo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.ForumCategoryPage;
import ru.itmo.selenium.pages.ForumPage;

public class ForumNavigationTest extends BaseTest {

    private static final String TARGET_CATEGORY = "О свободном доступе";

    @Test
    public void testForumCategoryNavigation() {
        ForumPage forumPage = new ForumPage(driver);
        forumPage.open();

        ForumCategoryPage categoryPage = forumPage.openCategory(TARGET_CATEGORY);

        String actualTitle = categoryPage.getActiveCategoryTitle();
        
        Assertions.assertTrue(actualTitle.contains(TARGET_CATEGORY),
                String.format("Ошибка: Заголовок открытой категории %s не совпадает с ожидаемым %s", actualTitle, TARGET_CATEGORY));
    }
}