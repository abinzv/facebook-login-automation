package com.abinesh.qa.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setup(String browser) {

        System.out.println("\n======================================");
        System.out.println("Starting browser: " + browser);
        System.out.println("======================================");

        if (browser.equalsIgnoreCase("chrome")) {

            driver = new ChromeDriver();

            System.out.println("Chrome browser opened.");

        } else if (browser.equalsIgnoreCase("firefox")) {

            driver = new FirefoxDriver();

            System.out.println("Firefox browser opened.");

        } else {

            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        }

        driver.manage().window().maximize();

        // Use explicit waits in Page Objects instead of implicit waits.
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(60)
        );

        driver.manage().timeouts().scriptTimeout(
                Duration.ofSeconds(30)
        );

        driver.get("https://www.facebook.com/");

        System.out.println("Navigated to Facebook.");
        System.out.println("Page title: " + driver.getTitle());
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {

            try {

                driver.quit();

                System.out.println("Browser closed.");

            } catch (Exception e) {

                System.out.println(
                        "Browser was already closed: "
                                + e.getMessage()
                );
            }

            driver = null;
        }
    }
}