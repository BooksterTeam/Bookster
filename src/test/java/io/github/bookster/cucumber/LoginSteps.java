package io.github.bookster.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bookster.config.BaseDriverIntegration;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

public class LoginSteps extends BaseDriverIntegration{

    private WebDriver browser;

    @Given("^the username is \"([^\"]*)\" and the password is \"([^\"]*)\"$")
    public void theUsernameIsAndThePasswordIs(String username, String password) throws Exception {
        browser = webDriver("login");
        browser.findElement(id("username")).sendKeys(username);
        browser.findElement(id("password")).sendKeys(password);
        Thread.sleep(500);
    }

    @When("^the sign in button is clicked$")
    public void theSignInButtonIsClicked() throws Exception {
        WebElement loginForm = browser.findElement(id("login-button"));
        loginForm.submit();
    }

    @Then("^the dashboard is shown$")
    public void the_dashboard_is_shown() throws Throwable {

    }

    @Then("^the sign in page is shown$")
    public void the_sign_in_page_is_shown() throws Throwable {
        Thread.sleep(500);
        browser.findElement(cssSelector(".alert.alert-danger"));
        assertThat(browser.getCurrentUrl(), is(server+ "login"));
    }

    @After
    public void tearDown() throws Exception {
        closeBrowser();
    }
}