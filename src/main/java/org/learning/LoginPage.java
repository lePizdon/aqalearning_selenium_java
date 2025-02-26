package org.learning;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(xpath = "//*[contains(@id, 'user-name')]")
    private WebElement loginField;

    @FindBy(xpath ="//*[contains(@id, 'password')]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[contains(@id, 'login-button')]")
    private WebElement loginButton;

    @Getter
    @FindBy(xpath = "//*[contains(@data-test, 'error')]")
    private WebElement errorHeader;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getErrorMessage() {
        return errorHeader.getText().trim();
    }

    public void login(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        loginButton.click();
    }

}
