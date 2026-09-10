import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class OrangeHRMLoginTest {

    WebDriver driver;
    WebDriverWait wait;

    private static final String BASE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    // Common locators
    By loginHeading = By.xpath("//h5[normalize-space(.)='Login']");
    By usernameField = By.xpath("//input[@name='username']");
    By passwordField = By.xpath("//input[@name='password']");
    By loginButton = By.xpath("//button[@type='submit']");
    By invalidCredentialsMessage =
            By.xpath("//p[normalize-space(.)='Invalid credentials']");
    By dashboardHeading =
            By.xpath("//h6[normalize-space(.)='Dashboard']");

    @BeforeMethod
    public void setUp() {

        // Open Chrome
        driver = new ChromeDriver();

        // Maximize the browser
        driver.manage().window().maximize();

        // Create explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Open OrangeHRM
        driver.get(BASE_URL);

        // Wait until the Login heading is visible
        WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginHeading)
        );

        // Store and print the page title
        String pageTitle = driver.getTitle();
        System.out.println("Page title: " + pageTitle);

        // Verify that the title is not empty
        Assert.assertFalse(
                pageTitle.trim().isEmpty(),
                "The page title is empty."
        );

        // Verify that the Login heading is displayed
        Assert.assertTrue(
                heading.isDisplayed(),
                "The Login heading is not displayed."
        );

        System.out.println("PASS: Login page is displayed.");
    }

    // Part B: Valid login
    @Test
    public void validLoginTest() {

        // Enter valid credentials
        login("Admin", "admin123");

        // Wait for Dashboard heading
        WebElement dashboard = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        dashboardHeading
                )
        );

        // Verify Dashboard is displayed
        Assert.assertTrue(
                dashboard.isDisplayed(),
                "Dashboard is not displayed."
        );

        // Verify the URL contains dashboard
        Assert.assertTrue(
                driver.getCurrentUrl().contains("dashboard"),
                "The current URL does not contain dashboard."
        );

        System.out.println("PASS: Valid user logged in successfully.");
        System.out.println("Current URL: " + driver.getCurrentUrl());

        // Click the profile menu
        By profileMenu =
                By.xpath("//*[contains(@class,'oxd-userdropdown-tab')]");

        wait.until(
                ExpectedConditions.elementToBeClickable(profileMenu)
        ).click();

        // Click Logout
        By logoutLink = By.xpath("//a[normalize-space(.)='Logout']");

        wait.until(
                ExpectedConditions.elementToBeClickable(logoutLink)
        ).click();

        // Verify that the Login page appears again
        WebElement headingAfterLogout = wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginHeading)
        );

        Assert.assertTrue(
                headingAfterLogout.isDisplayed(),
                "Login page is not displayed after logout."
        );

        System.out.println("PASS: User logged out successfully.");
    }

    // Part C: Invalid username
    @Test
    public void invalidUsernameTest() {

        login("WrongAdmin", "admin123");

        verifyInvalidLogin();

        System.out.println(
                "PASS: Login was rejected for an invalid username."
        );
    }

    // Part C: Invalid password
    @Test
    public void invalidPasswordTest() {

        login("Admin", "wrongPassword");

        verifyInvalidLogin();

        System.out.println(
                "PASS: Login was rejected for an invalid password."
        );
    }

    // Part C: Both username and password invalid
    @Test
    public void bothCredentialsInvalidTest() {

        login("WrongAdmin", "wrongPassword");

        verifyInvalidLogin();

        System.out.println(
                "PASS: Login was rejected when both credentials were invalid."
        );
    }

    // Part D: Empty username
    @Test
    public void emptyUsernameTest() {

        // Empty string means no username is entered
        login("", "admin123");

        By usernameRequiredMessage = By.xpath(
                "//input[@name='username']" +
                        "/ancestor::div[contains(@class,'oxd-input-group')]" +
                        "//span[normalize-space(.)='Required']"
        );

        WebElement requiredMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        usernameRequiredMessage
                )
        );

        Assert.assertEquals(
                requiredMessage.getText().trim(),
                "Required",
                "Required message is not displayed for username."
        );

        System.out.println(
                "PASS: Required message appeared for the empty username."
        );
    }

    // Part D: Empty password
    @Test
    public void emptyPasswordTest() {

        // Empty string means no password is entered
        login("Admin", "");

        By passwordRequiredMessage = By.xpath(
                "//input[@name='password']" +
                        "/ancestor::div[contains(@class,'oxd-input-group')]" +
                        "//span[normalize-space(.)='Required']"
        );

        WebElement requiredMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        passwordRequiredMessage
                )
        );

        Assert.assertEquals(
                requiredMessage.getText().trim(),
                "Required",
                "Required message is not displayed for password."
        );

        System.out.println(
                "PASS: Required message appeared for the empty password."
        );
    }

    // Part D: Both fields empty
    @Test
    public void bothFieldsEmptyTest() {

        login("", "");

        By requiredMessages =
                By.xpath("//span[normalize-space(.)='Required']");

        // Wait until two Required messages are available
        wait.until(
                ExpectedConditions.numberOfElementsToBe(
                        requiredMessages,
                        2
                )
        );

        // Use findElements() because we need to count the messages
        List<WebElement> messages =
                driver.findElements(requiredMessages);

        System.out.println(
                "Number of Required messages: " + messages.size()
        );

        Assert.assertEquals(
                messages.size(),
                2,
                "Two Required messages should be displayed."
        );

        System.out.println(
                "PASS: Two Required messages appeared."
        );
    }

    // Reusable method for entering credentials and clicking Login
    public void login(String username, String password) {

        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        usernameField
                )
        );

        WebElement passwordInput =
                driver.findElement(passwordField);

        // Clear existing values
        usernameInput.clear();
        passwordInput.clear();

        // Enter test data
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);

        // Click Login
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();

    }

    // Reusable method for invalid-login verification
    public void verifyInvalidLogin() {

        // Wait for the error message
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        invalidCredentialsMessage
                )
        );

        System.out.println(
                "Error message: " + errorMessage.getText()
        );

        // Verify error text
        Assert.assertTrue(
                errorMessage.getText()
                        .contains("Invalid credentials"),
                "Invalid credentials message is not displayed."
        );

        // Verify that the user remains on the login page
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/auth/login"),
                "The user did not remain on the login page."
        );

        // Verify that the Dashboard is not displayed
        List<WebElement> dashboardHeadings =
                driver.findElements(dashboardHeading);

        Assert.assertTrue(
                dashboardHeadings.isEmpty(),
                "Dashboard should not be displayed after an invalid login."
        );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        // Close the complete browser session
        if (driver != null) {
            driver.quit();
        }

        System.out.println("Browser closed.");
        System.out.println("--------------------------------");
    }
}