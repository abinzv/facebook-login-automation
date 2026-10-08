# 📘 Facebook Login Automation Framework

A robust, data-driven Selenium WebDriver automation framework built to validate authentication flows. This project demonstrates industry best practices in test automation, including the Page Object Model (POM), explicit waits, and cross-browser execution.

## 🛠️ Tech Stack
- **Language:** Java (JDK 21)
- **Automation Tool:** Selenium WebDriver
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **Design Pattern:** Page Object Model (POM)

## ✨ Key Features
- **Cross-Browser Testing:** Seamlessly executes tests on both Google Chrome and Mozilla Firefox via `testng.xml` parameters.
- **Robust Element Interaction:** Implements a smart fallback mechanism (JavaScript Executor) to handle "Element Click Intercepted" errors caused by dynamic overlays or cookie banners.
- **Explicit Waits:** Utilizes `WebDriverWait` for reliable synchronization, eliminating flaky `Thread.sleep()` calls.
- **Clean Architecture:** Strict separation of page locators/actions (`FacebookLoginPage.java`) and test logic (`FacebookLoginTest.java`).

## 📂 Project Structure
```text
facebook-login-automation/
├── src/
│   ├── main/java/com/abinesh/qa/pages/
│   │   └── FacebookLoginPage.java      # Page Object Class (Locators & Actions)
│   └── test/java/com/abinesh/qa/tests/
│       ├── BaseTest.java               # Browser setup, teardown, and configuration
│       └── FacebookLoginTest.java      # TestNG Test Cases
├── src/test/resources/
│   └── testng.xml                      # Test suite configuration (Chrome & Firefox)
├── pom.xml                             # Maven dependencies (Selenium, TestNG, WebDriverManager)
└── README.md