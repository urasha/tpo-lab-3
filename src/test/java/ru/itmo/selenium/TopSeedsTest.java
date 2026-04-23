package ru.itmo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.selenium.pages.HomePage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopSeedsTest extends BaseTest {

    @Test
    public void testTopSeedsAreSortedDescending() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        
        List<Integer> actualRatings = homePage.getTopSeedsRatings();
        Assertions.assertFalse(actualRatings.isEmpty());

        List<Integer> expectedRatings = new ArrayList<>(actualRatings);
        expectedRatings.sort(Collections.reverseOrder());

        Assertions.assertEquals(expectedRatings, actualRatings,
                "Рейтинги 'Топ раздач' не отсортированы по убыванию!");
    }
}