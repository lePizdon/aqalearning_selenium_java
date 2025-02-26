package org.learning;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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

    public boolean menuIsPresent() {
        return menuButton.isDisplayed();
    }

    public void entryMenu() {
        menuButton.click();
    }

    public void handleLogout() {
        logoutButton.click();
    }

    public boolean isCartBadgeAbsent() {
        return driver.findElements(By.className("shopping_cart_badge")).isEmpty();
    }

    public Integer addAllItemsAndReturnThouCounter() {
        List<WebElement> items = driver.findElements(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory"));
        Integer counter = items.size();
        items.forEach(WebElement::click);
        return counter;
    }

    public void removeAllItems() {
        driver.findElements(By.cssSelector(".btn.btn_secondary.btn_small.btn_inventory"))
                .forEach(WebElement::click);
    }

    public Integer getCartCounter() {
        return Integer.parseInt(cartBadge.getText().trim());
    }
}
