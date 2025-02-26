import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.learning.ConfProperties;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EdgeCaseBehaviorTest extends BaseTestCaseTemplate{

    @AfterEach
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void lockedOutUserLogin_LoginFailedWithErrorMessage() {
        loginPage.login(ConfProperties.getProperty("locked_out_user_login"),
                ConfProperties.getProperty("password"));
        assertTrue(loginPage.getErrorHeader().isDisplayed());
    }

    @Test
    public void problemUserLogin_LoginSuccessful() {
        
    }
}
