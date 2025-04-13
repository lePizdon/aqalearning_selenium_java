package com.aqa;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class BaseTestTemplate {
    protected static WebDriver webDriver;
    protected static WebDriverWait webDriverWait;
    protected static SoftAssertions softly;
    protected static Logger logger;
    protected static ChromeOptions options;
}
