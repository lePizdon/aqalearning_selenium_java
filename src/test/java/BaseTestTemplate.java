import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.learning.ConfProperties;
import org.learning.MessageBundle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class BaseTestTemplate {
    static WebDriver webDriver;
    static WebDriverWait webDriverWait;
    static SoftAssertions softly;

    @BeforeAll
    public static void initEnvironment() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--enable-logging");
        options.addArguments("--v=1");
        webDriver = new ChromeDriver();
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        softly = new SoftAssertions();
    }
}
