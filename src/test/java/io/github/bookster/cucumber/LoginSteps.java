package io.github.bookster.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bookster.config.BaseDriverIntegration;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.core.IsNot.not;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

public class LoginSteps {

    WebDriver browser;

    @Before
    public void setUp() throws Exception {
        browser = BaseDriverIntegration.chromeDriver();
        browser.get("http://localhost:8080/#/login");
    }

    @Given("^the username is \"([^\"]*)\" and the password is \"([^\"]*)\"$")
    public void theUsernameIsAndThePasswordIs(String username, String password) throws Exception {
        browser.findElement(id("username")).sendKeys(username);
        browser.findElement(id("password")).sendKeys(password);
    }

    @When("^the login button is clicked$")
    public void theLoginButtonIsClicked() throws Exception {
        WebElement loginForm = browser.findElement(id("login-button"));
        loginForm.submit();
    }

    @Then("^the dashboard is shown$")
    public void the_dashboard_is_shown() throws Throwable {


    }

    @Then("^the error message is \"([^\"]*)\"")
    public void theErrorMessageIsShown(String message) throws Exception {
        WebElement alertBox = browser.findElement(className("alert alert-danger "));
        Assert.assertThat(alertBox, not(null));
    }

    @After
    public void tearDown() throws Exception {
        browser.quit();
    }
}