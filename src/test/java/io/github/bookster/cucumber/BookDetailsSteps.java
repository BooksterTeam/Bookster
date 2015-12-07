package io.github.bookster.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bookster.config.BaseDriverIntegration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static java.lang.Integer.valueOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.By.id;

/**
 * Created on 03/12/15
 * author: nixoxo
 */
public class BookDetailsSteps extends BaseDriverIntegration {

    private WebDriver browser;

    @Before
    public void setUp() throws Exception {
        browser = chromeDriver();
        browser.get(url + "login");
    }

    @Given("^user authenticated and navigated to the market$")
    public void userIsAdminAndNavigatedToTheMarket() throws Throwable {
        browser.findElement(id("username")).sendKeys("admin");
        browser.findElement(id("password")).sendKeys("admin");
        WebElement loginForm = browser.findElement(id("login-button"));
        loginForm.submit();
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @When("^user lookup a book with id '(\\d+)'$")
    public void userLookupABookWithId(int bookid
    ) throws Throwable {
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Thread.sleep(5000);
        browser.get("http://localhost:3000/#/market");
        WebElement detailsButton = browser.findElement(id("details" + bookid));
        detailsButton.click();

    }

    @Then("^the title of the book is 'Master Software Engineering'$")
    public void theTitleOfTheBookIsMasterSoftwareEngineering() throws Throwable {
        WebElement title = browser.findElement(id("book-title"));
        assertThat(title.getText(), is("Master Software Engineering"));
    }

    @Then("^the isbn of the book is '(\\d+)'$")
    public void theIsbnOfTheBookIs(int isbn) throws Throwable {
        WebElement title = browser.findElement(id("book-isbn"));
        assertThat(valueOf(title.getText()), is(isbn));
    }

    @After
    public void tearDown() throws Exception {
        browser.quit();
    }
}
