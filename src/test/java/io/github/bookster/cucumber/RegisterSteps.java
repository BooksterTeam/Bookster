package io.github.bookster.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import io.github.bookster.config.BaseDriverIntegration;
import io.github.bookster.domain.User;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertThat;
import static org.openqa.selenium.By.id;

@RunWith(Cucumber.class)
public class RegisterSteps extends BaseIntegration {

    private WebDriver browser;

    @Before
    public void setUp() throws Exception {
        browser = new BaseDriverIntegration().chromeDriver();
        browser.get("http://localhost:8080/#/register");
    }

    @Given("^the username is \"([^\"]*)\" and the email is \"([^\"]*)\" and the password \"([^\"]*)\"$")
    public void the_username_is_something_and_the_email_is_something_and_the_password_something(String username, String email, String password) throws Throwable {
        Random random = new Random();
        username = username + "" + random.nextInt(10000) + 5;
        browser.findElement(id("login")).sendKeys(username);
        browser.findElement(id("email")).sendKeys(email + random.nextInt(1000));
        browser.findElement(id("password")).sendKeys(password);
        browser.findElement(id("confirmPassword")).sendKeys(password);
    }

    @When("^the register button is clicked$")
    public void the_register_button_is_clicked() throws Throwable {
        WebElement registerForm = browser.findElement(id("register-button"));
        registerForm.submit();
    }

    @Then("^the register page is shown and the cssSelector is \"([^\"]*)\" and the message is \"([^\"]*)\"$")
    public void theRegisterPageIsShownAndTheCssSelectorIsAndTheMessageIs(String cssSelector, String message) throws Throwable {
        Thread.sleep(1000);
        WebElement element = browser.findElement(id(cssSelector));
        String text = element.getText();
        assertThat(text.contains(message), Matchers.is(true));
    }

    @After
    public void tearDown() throws Exception {
        browser.quit();
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusMinutes(1));
        for (User user1 : users) {
            userRepository.delete(user1);
        }
    }

    @Then("^the register page is shown$")
    public void theRegisterPageIsShown() throws Throwable {
        Thread.sleep(1000);
        WebElement registerForm = browser.findElement(id("register-button"));
        String disabled = registerForm.getAttribute("aria-disabled");
        assertThat(disabled, Matchers.is("true"));
    }
}