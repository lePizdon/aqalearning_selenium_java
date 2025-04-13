package com.aqa.happypath;

import com.aqa.BaseTestTemplate;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.learning.ConfProperties;
import org.learning.LandingPage;
import org.learning.Log;
import org.learning.LoginPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.opentest4j.AssertionFailedError;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LandingUITest extends BaseTestTemplate {
    private static LandingPage landingPage;

    @BeforeAll
    public static void login() {
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        options.addArguments("--incognito");
        webDriver = new ChromeDriver(options);
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        softly = new SoftAssertions();
        logger = Log.getLogger(LandingUITest.class);

        webDriver.get(ConfProperties.getProperty("loginpage"));
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login(ConfProperties.getProperty("user.standard"), ConfProperties.getProperty("password"));
    }

    @BeforeEach
    public void initPomPage() {
        landingPage = new LandingPage(webDriver);
    }

    @Test
    @Order(1)
    public void addAllItemsOnThePageToCart_allItemsAddedToCart() {
        Assertions.assertEquals(webDriver.getCurrentUrl(), ConfProperties.getProperty("landingpage"));
        logger.info("Successful redirect after login");
        softly.assertThat(landingPage.menuIsPresent()).isTrue();
        softly.assertThat(landingPage.appLogoIsPresent()).isTrue();

        if (landingPage.menuIsPresent() && landingPage.appLogoIsPresent()) {
            logger.info("Landing page rendered");
        } else {
            logger.debug("Some elements if not all did not render fully");
        }

        Integer itemsCounter = landingPage.addAllItemsAndReturnThouCounter();

        webDriverWait.until(ExpectedConditions.visibilityOf(landingPage.cartBadge));
        softly.assertThat(itemsCounter).isEqualTo(landingPage.getCartCounter());

        if (itemsCounter.equals(landingPage.getCartCounter())) {
            logger.info("Counters are equal");
        } else {
            logger.error("Discrepancy between counters - anomaly");
        }

        try {
            softly.assertAll();
            logger.info("All assertions passed");
        } catch (AssertionError e) {
            logger.error("Some assertions failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test
    @Order(2)
    public void removeAllItemsOnPageFromCart_allItemsRemovedFromCart() {
        landingPage.removeAllItems();
        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(landingPage.cartBadge));
            assertEquals(0, landingPage.getCartCounter());
        } catch (TimeoutException e) {
            assertTrue(landingPage.isCartBadgeAbsent());
        }
    }

    @Test
    @Order(3)
    public void clickOnItemRedirectBack_successfulRedirectOnItemPageAndBack() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(landingPage.getProductImages().getFirst()));

        landingPage.getProductImages().getFirst().click();

        Assertions.assertFalse(webDriver.getCurrentUrl().equals(ConfProperties.getProperty("landingpage")));

        webDriver.navigate().back();

        Assertions.assertEquals(webDriver.getCurrentUrl(), ConfProperties.getProperty("landingpage"));
    }

    @Test
    @Order(4)
    public void clickOnAllItemsWithRedirect_successfulRedirectOnItemPagesAndBack() {
        List<WebElement> productImages = landingPage.getProductImages();

        for (int i = 0; i < productImages.size(); i++) {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(landingPage.getProductImages().get(i)));

            landingPage.getProductImages().get(i).click();

            Assertions.assertFalse(webDriver.getCurrentUrl().equals(ConfProperties.getProperty("landingpage")));

            webDriver.navigate().back();

            try {
                webDriverWait.until(ExpectedConditions.urlToBe(ConfProperties.getProperty("landingpage")));
            } catch (TimeoutException e) {
                throw new AssertionFailedError("Failed to redirect to landing");
            }
            Assertions.assertEquals(webDriver.getCurrentUrl(), ConfProperties.getProperty("landingpage"));
        }
    }

    @AfterAll
    public static void teardown() {
        webDriver.manage().deleteAllCookies();
        webDriver.quit();
    }
}
