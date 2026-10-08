package com.abinesh.qa.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FacebookLoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Facebook login fields
    private final By emailField = By.cssSelector(
            "input[name='email'], " +
            "input[id='email'], " +
            "input[id='m_login_email']"
    );

    private final By passwordField = By.cssSelector(
            "input[name='pass'], " +
            "input[id='pass'], " +
            "input[id='m_login_password']"
    );

    /*
     * Facebook changes its DOM frequently.
     * Use several attributes/text patterns instead of depending
     * on only one CSS selector.
     */
    private final By loginButton = By.xpath(
            "//button[@name='login']" +
            " | //button[.//span[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'log in')]]" +
            " | //div[@role='button' and contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'log in')]" +
            " | //input[@type='submit']"
    );

    private final By cookieAcceptButton = By.xpath(
            "//button[contains(translate(normalize-space(.), " +
            "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'allow')]" +
            " | //button[contains(translate(normalize-space(.), " +
            "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'accept')]"
    );

    public FacebookLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /**
     * Handles cookie/consent popup if it appears.
     */
    public void handleCookieBanner() {
        try {
            WebDriverWait shortWait = new WebDriverWait(
                    driver,
                    Duration.ofSeconds(3)
            );

            WebElement cookieButton = shortWait.until(
                    ExpectedConditions.elementToBeClickable(cookieAcceptButton)
            );

            cookieButton.click();

            System.out.println("Cookie/consent button clicked.");

        } catch (Exception e) {
            System.out.println("No cookie banner displayed.");
        }
    }

    public void enterEmail(String email) {

        handleCookieBanner();

        WebElement emailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailField)
        );

        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {

        WebElement passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField)
        );

        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {

    WebElement loginBtn = wait.until(
            ExpectedConditions.presenceOfElementLocated(loginButton)
    );

    // Bring the button into the visible area
    ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    loginBtn
            );

    try {
        // First try normal Selenium click
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();

    } catch (org.openqa.selenium.ElementClickInterceptedException e) {

        System.out.println(
                "Normal click intercepted. Using JavaScript click..."
        );

        // Facebook has an overlay covering the button,
        // so use JavaScript as a fallback.
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        loginBtn
                );
    }

    System.out.println("Login button clicked.");
}

    public void performLogin(String email, String password) {

        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    /**
     * Checks whether the browser is still on a Facebook login/authentication page.
     *
     * This is deliberately URL-based because Facebook may display different
     * error messages depending on the account/login flow.
     */
    public boolean isLoginPageDisplayed() {

        try {

            wait.until(driver -> {
                String url = driver.getCurrentUrl();

                return url.contains("facebook.com")
                        && (
                        url.contains("login")
                        || url.contains("facebook.com/")
                );
            });

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks whether the login fields are still present.
     */
    public boolean areLoginFieldsDisplayed() {

        try {

            boolean emailDisplayed = driver.findElements(emailField)
                    .stream()
                    .anyMatch(WebElement::isDisplayed);

            boolean passwordDisplayed = driver.findElements(passwordField)
                    .stream()
                    .anyMatch(WebElement::isDisplayed);

            return emailDisplayed && passwordDisplayed;

        } catch (Exception e) {
            return false;
        }
    }
}