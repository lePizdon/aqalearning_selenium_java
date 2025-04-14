package org.learning;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LandingPage {

    private WebDriver driver;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Getter
    @FindBy(xpath = "//*[contains(@id, 'react-burger-menu-btn')]")
    public WebElement menuButton;

    @Getter
    @FindBy(xpath = "//*[contains(@id, 'logout_sidebar_link')]")
    public WebElement logoutButton;

    @Getter
    @FindBy(xpath = "//span[contains(@class, 'shopping_cart_badge')]")
    public WebElement cartBadge;

    @Getter
    @FindBy(xpath = "//div[contains(@class, 'app_logo')]")
    public WebElement appLogo;

    @Getter
    @FindBy(xpath = "//div[contains(@class, 'inventory_item_img')]")
    public List<WebElement> productImages;

    @Getter
    @FindBy(xpath = "//div[contains(@class, 'inventory_item_name')]")
    public List<WebElement> productNames;

    @Getter
    @FindBy(xpath = "//div[contains(@class, 'inventory_item')]")
    public List<WebElement> products;

    @Getter
    @FindBy(xpath = "//button[contains(@id, 'add-to-cart')]")
    public List<WebElement> addButtons;

    @Getter
    @FindBy(xpath = "//button[contains(@id, 'remove')]")
    public List<WebElement> removeButtons;

    public boolean menuIsPresent() {
        return menuButton.isDisplayed();
    }

    public boolean appLogoIsPresent() { return appLogo.isDisplayed(); }

    public void entryMenu() {
        menuButton.click();
    }

    public void handleLogout() {
        logoutButton.click();
    }

    public boolean isCartBadgeAbsent() {
        return driver.findElements(By.className("shopping_cart_badge")).isEmpty();
    }

    public Integer getCartCounter() {
        return Integer.parseInt(cartBadge.getText().trim());
    }
}
