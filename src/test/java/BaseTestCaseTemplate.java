import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.learning.ConfProperties;
import org.learning.LandingPage;
import org.learning.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BaseTestCaseTemplate {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static LoginPage loginPage;
    protected static LandingPage landingPage;

    @BeforeAll
    public static void setup() {
        wait = EnvironmentFactory.createWait(driver);
        driver = EnvironmentFactory.createChromeDriver();
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        driver.get(ConfProperties.getProperty("loginpage"));
    }
}
