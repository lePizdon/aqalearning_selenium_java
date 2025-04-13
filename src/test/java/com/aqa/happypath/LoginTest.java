package com.aqa.happypath;

import com.aqa.BaseTestTemplate;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.learning.ConfProperties;
import org.learning.Log;
import org.learning.LoginPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends BaseTestTemplate {
    static LoginPage loginPage;

    @BeforeAll
    public static void login() {
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        options.addArguments("--incognito");
        webDriver = new ChromeDriver(options);
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        softly = new SoftAssertions();
    }

    @BeforeEach
    public void initDomPages() {
        logger = Log.getLogger(LoginTest.class);
        loginPage = new LoginPage(webDriver);
    }

    @Test
    @Order(1)
    public void login_withValidCredentials_loginAndRedirectSuccessful() {
        webDriver.get(ConfProperties.getProperty("loginpage"));
        loginPage.login(ConfProperties.getProperty("user.standard"));

        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(loginPage.getErrorHeader()));
            softly.assertThat(loginPage.getErrorHeader().isDisplayed()).isFalse();
            if (loginPage.getErrorHeader().isDisplayed()) {
                logger.error("Error during login with valid credentials - anomaly");
            }
        } catch (TimeoutException e) {
            logger.info("Login successful with valid credentials - standard behaviour");
            softly.assertThat(webDriver.getCurrentUrl()).isNotEqualTo(ConfProperties.getProperty("loginpage"));
        }
        if (!webDriver.getCurrentUrl().equals(ConfProperties.getProperty("loginpage"))) {
            logger.info("Successful redirect to landing - standard behaviour");
        } else {
            logger.error("Unsuccessful redirect to landing - anomaly");
        }
        try {
            softly.assertAll();
            logger.info("All asserts were successful");
        } catch (AssertionError e) {
            logger.error("There is error during asserts: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test
    @Order(2)
    public void login_withInvalidCredentials_loginFailedMessageDisplayedRedirectFailed() {
        webDriver.get(ConfProperties.getProperty("loginpage"));
        loginPage.login("1234", "1234");
//        loginPage.login(ConfProperties.getProperty("user.standard"), ConfProperties.getProperty("password"));
        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(loginPage.getErrorHeader()));
            softly.assertThat(loginPage.getErrorHeader().isDisplayed()).isTrue();
            logger.info("Error during login with bad credentials - standard behaviour");
            softly.assertThat(loginPage.getErrorMessage().isEmpty()).isFalse();
            logger.info("Error message: {}", loginPage.getErrorMessage());
        } catch (TimeoutException e) {
            softly.assertThat(webDriver.getCurrentUrl()).isEqualTo(ConfProperties.getProperty("loginpage"));
        }
        if (webDriver.getCurrentUrl().equals(ConfProperties.getProperty("loginpage"))) {
            logger.info("Wasn't redirected due to failed login - standard behaviour");
        } else {
            logger.error("Redirected despite failed login - anomaly");
        }
        try {
            softly.assertAll();
            logger.info("All asserts were successful");
        } catch(AssertionError e) {
            logger.error("There is error during asserts: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test
    @Order(3)
    public void login_lockedOutUser_loginLockedMessageDisplayedRedirectFailed() {
        webDriver.get(ConfProperties.getProperty("loginpage"));
        loginPage.login(ConfProperties.getProperty("user.locked.out"), ConfProperties.getProperty("password"));

        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(loginPage.getErrorHeader()));
            softly.assertThat(loginPage.getErrorHeader().isDisplayed()).isTrue();
            logger.info("Error during login with locked account - standard behaviour");
            try {
                softly.assertThat(loginPage.getErrorMessage().isEmpty()).isFalse();
            } catch (AssertionError e) {
                logger.error("Message wasn't displayed - anomaly");
            }
            if (loginPage.getErrorMessage().isEmpty()) {
                logger.error("Empty error message - anomaly");
            } else {
                logger.info("Error message displayed: {}", loginPage.getErrorMessage());
            }
        } catch (TimeoutException e) {
            softly.assertThat(webDriver.getCurrentUrl()).isEqualTo(ConfProperties.getProperty("loginpage"));
        }
        if (webDriver.getCurrentUrl().equals(ConfProperties.getProperty("loginpage"))) {
            logger.info("Wasn't redirected due to failed login - standard behaviour");
        } else {
            logger.error("Redirected - anomaly");
        }
        try {
            softly.assertAll();
            logger.info("All asserts were successful");
        } catch (AssertionError e) {
            logger.error("There is error during asserts: {}", e.getMessage(), e);
            throw e;
        }
    }

    @AfterAll
    public static void teardown() {
        webDriver.quit();
    }
}
