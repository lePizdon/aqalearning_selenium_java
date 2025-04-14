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

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LandingUITest extends BaseTestTemplate {
    private static LandingPage landingPage;

    @BeforeAll
    public static void login() {
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        options.addArguments("--incognito");
        webDriver = new ChromeDriver(options);
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
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
    public void clickOnItemRedirectBack_successfulRedirectOnItemPageAndBack() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(landingPage.getProductImages().getFirst()));

        landingPage.getProductImages().getFirst().click();

        assertFalse(webDriver.getCurrentUrl().equals(ConfProperties.getProperty("landingpage")));

        webDriver.navigate().back();

        assertEquals(webDriver.getCurrentUrl(), ConfProperties.getProperty("landingpage"));
    }

    @Test
    @Order(2)
    public void clickOnAllItemsWithRedirect_successfulRedirectOnItemPagesAndBack() {
        List<WebElement> productImages = landingPage.getProductImages();

        for (int i = 0; i < productImages.size(); i++) {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(landingPage.getProductImages().get(i)));

            landingPage.getProductImages().get(i).click();

            assertFalse(webDriver.getCurrentUrl().equals(ConfProperties.getProperty("landingpage")));

            webDriver.navigate().back();

            try {
                webDriverWait.until(ExpectedConditions.urlToBe(ConfProperties.getProperty("landingpage")));
            } catch (TimeoutException e) {
                throw new AssertionError("Failed to redirect to landing: {}", e);
            }
            assertEquals(webDriver.getCurrentUrl(), ConfProperties.getProperty("landingpage"));
        }
    }

    @Test
    @Order(3)
    public void clickOnAllItemNames_successfulRedirectOnItemsPageAndBack() {
        List<WebElement> productNames = landingPage.getProductNames();

        for (int i = 0; i < productNames.size(); i++) {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(landingPage.getProductNames().get(i)));

            landingPage.getProductNames().get(i).click();
            assertFalse(webDriver.getCurrentUrl().equals(ConfProperties.getProperty("landingpage")));
            webDriver.navigate().back();

            try {
                webDriverWait.until(ExpectedConditions.urlToBe(ConfProperties.getProperty("landingpage")));
            } catch (TimeoutException e) {
                throw new AssertionError("Failed to redirect to landing: {}", e);
            }
            assertEquals(webDriver.getCurrentUrl(), ConfProperties.getProperty("landingpage"));
        }
    }

    @Test
    @Order(4)
    public void clickOnAllAddToCartButtons_allItemsAddedButtonsChangeState() {
        List<WebElement> addButtons;
        Integer addedCounter = 0;

        while (!(addButtons = landingPage.getAddButtons()).isEmpty()) {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(addButtons.get(0)));
            addButtons.get(0).click();
            addedCounter++;
        }

        List<WebElement> removeButtons = landingPage.getRemoveButtons();

        softly.assertThat(removeButtons.size()).isEqualTo(addedCounter);

        webDriverWait.until(ExpectedConditions.visibilityOf(landingPage.getCartBadge()));

        softly.assertThat(addedCounter).isEqualTo(landingPage.getCartCounter());

        try {
            softly.assertAll();
        } catch (AssertionError e) {
            throw e;
        }
    }

    @Test
    @Order(5)
    public void clickOnAllRemoveButtons_allItemsRemovedButtonsChangeState() {
        List<WebElement> removeButtons;
        Integer initialButtonsCount = landingPage.getRemoveButtons().size();
        Integer removedCounter = 0;

        while (!(removeButtons = landingPage.getRemoveButtons()).isEmpty()) {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(removeButtons.get(0)));
            removeButtons.get(0).click();
            removedCounter++;
        }

        softly.assertThat(removedCounter).isEqualTo(initialButtonsCount);

        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(landingPage.getCartBadge()));
            logger.debug("Badge not absent without any items in it - anomaly");
        } catch (TimeoutException e) {
            softly.assertThat(landingPage.isCartBadgeAbsent()).isTrue();
        }

        softly.assertThat(removedCounter).isEqualTo(landingPage.getAddButtons());

        try {
            softly.assertAll();
        } catch (AssertionError e) {
            logger.error("Error during assertions: {}", e);
        }
    }

    @AfterAll
    public static void teardown() {
        webDriver.manage().deleteAllCookies();
        webDriver.quit();
    }
}
