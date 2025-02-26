import org.junit.jupiter.api.*;
import org.learning.ConfProperties;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StandardBehaviorTest extends BaseTestCaseTemplate {
    @Test
    @Order(1)
    public void login_SuccessfulLogin() {
        loginPage.login(ConfProperties.getProperty("standard_user_login"),
                ConfProperties.getProperty("password"));
        wait.until(ExpectedConditions.visibilityOf(landingPage.getMenuButton()));
        assertTrue(landingPage.menuIsPresent());
    }

    @Test
    @Order(2)
    public void addAllProductsToCart_AllProductsAddedToCart() {
        Integer itemsToAdd = landingPage.addAllItemsAndReturnThouCounter();
        wait.until(ExpectedConditions.visibilityOf(landingPage.getCartBadge()));
        assertTrue(landingPage.getCartBadge().isDisplayed());
        assertEquals(itemsToAdd, landingPage.getCartCounter());
    }

    @Test
    @Order(3)
    public void removeAllProductsFromLanding_AllProductsRemovedFromCart() {
        landingPage.removeAllItems();
        assertTrue(landingPage.isCartBadgeAbsent());
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
