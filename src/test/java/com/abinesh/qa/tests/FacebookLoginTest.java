package com.abinesh.qa.tests;

import com.abinesh.qa.pages.FacebookLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FacebookLoginTest extends BaseTest {

    @Test(priority = 1)
    public void testInvalidLogin() {

        System.out.println("\nStarting Test: Invalid Login");

        FacebookLoginPage loginPage =
                new FacebookLoginPage(driver);

        loginPage.performLogin(
                "invalid_email@test.com",
                "wrongpassword123"
        );

        boolean stillOnLoginPage =
                loginPage.isLoginPageDisplayed();

        Assert.assertTrue(
                stillOnLoginPage,
                "Invalid login should not successfully authenticate."
        );

        System.out.println(
                "Test Passed: Invalid login rejected."
        );
    }

    @Test(priority = 2)
    public void testEmptyFields() {

        System.out.println("\nStarting Test: Empty Fields Login");

        FacebookLoginPage loginPage =
                new FacebookLoginPage(driver);

        loginPage.handleCookieBanner();

        loginPage.clickLogin();

        boolean loginFieldsStillDisplayed =
                loginPage.areLoginFieldsDisplayed();

        Assert.assertTrue(
                loginFieldsStillDisplayed,
                "Empty login should not successfully authenticate."
        );

        System.out.println(
                "Test Passed: Empty fields rejected."
        );
    }

    @Test(priority = 3)
    public void testValidFormatFakeAccount() {

        System.out.println(
                "\nStarting Test: Valid Format Fake Account"
        );

        FacebookLoginPage loginPage =
                new FacebookLoginPage(driver);

        loginPage.performLogin(
                "testuser123@gmail.com",
                "ValidPassword123!"
        );

        boolean stillOnLoginPage =
                loginPage.isLoginPageDisplayed();

        Assert.assertTrue(
                stillOnLoginPage,
                "Fake account should not successfully authenticate."
        );

        System.out.println(
                "Test Passed: Fake account rejected."
        );
    }
}