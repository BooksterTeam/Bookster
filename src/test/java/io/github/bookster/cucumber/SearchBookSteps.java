package io.github.bookster.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.github.bookster.config.BaseDriverIntegration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.id;

/**
 * Created on 03/12/15
 * author: nixoxo
 */
public class SearchBookSteps extends BaseDriverIntegration {

    private WebDriver browser;

    @Before
    public void setUp() throws Exception {
        browser = chromeDriver();
        browser.get(url + "login");
    }

    @Given("^user is authenticated and navigated to the market$")
    public void userIsAuthenticatedAndNavigatedToTheMarket() throws Throwable {
        authenticate(browser);
        Thread.sleep(1000);
        browser.get("http://localhost:3000/#/market");
    }


    @Given("^user search for 'gesch'$")
    public void userSearchForGesch() throws Throwable {
        Thread.sleep(1000);
        browser.findElement(id("book-query")).sendKeys("gesch");
        Thread.sleep(1000);

    }

    @Then("^a book is found which has the id 'details(\\d+)'$")
    public void aBookIsFoundWhichHasTheIdDetails(int bookid) throws Throwable {
        WebElement element = browser.findElement(id("details" + bookid));
        assertTrue(element.isDisplayed());
    }

    @After
    public void tearDown() throws Exception {
        browser.quit();
    }

    @Given("^user search for 'jqwerajksndfnjk'$")
    public void userSearchForJqwerajksndfnjk() throws Throwable {
        Thread.sleep(1000);
        browser.findElement(id("book-query")).sendKeys("jqwerajksndfnjk");
        Thread.sleep(1000);
    }


    @Then("^no book is found$")
    public void noBookIsFound() throws Throwable {
        WebElement element = browser.findElement(id("book-market"));
    }
}
