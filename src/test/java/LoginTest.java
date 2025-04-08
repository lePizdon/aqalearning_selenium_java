import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.ConfProperties;
import org.learning.LandingPage;
import org.learning.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTestTemplate {
    static LoginPage loginPage;

    @BeforeEach
    public void initDomPages() {
        webDriver.get(ConfProperties.getProperty("loginpage"));
        loginPage = new LoginPage(webDriver);
    }
    @Test
    public void login_withValidCredentials_loginSuccessful() {
        loginPage.login(ConfProperties.getProperty("user.locked.out"));

        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(loginPage.getErrorHeader()));
            softly.assertThat(loginPage.getErrorHeader().isDisplayed()).isFalse();
        } catch (TimeoutException e) {
            softly.assertThat(webDriver.getCurrentUrl()).isNotEqualTo(ConfProperties.getProperty("loginpage"));
        }

        softly.assertAll();
    }

    @AfterEach
    public void teardown() {
        webDriver.quit();
    }
}
