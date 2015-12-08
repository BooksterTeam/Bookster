package io.github.bookster.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.github.bookster.config.BaseDriverIntegration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created on 03/12/15
 * author: nixoxo
 */
public class CopysSteps extends BaseDriverIntegration {

    private WebDriver browser;

    @Given("^user authenticated and navigated to the copys$")
    public void userAuthenticatedAndNavigatedToTheCopys() throws Throwable {
        browser = webDriver();
        authenticate(browser);
        Thread.sleep(1000);
        browser.get("http://localhost:3000/#/copys");
    }

    @Given("^a modal does pop up$")
    public void aModalDoesPopUp() throws Throwable {
        Thread.sleep(1000);
        browser.get("http://localhost:3000/#/copys/new");
    }

    @Given("^add a copy for the book with the id '(\\d+)'$")
    public void addACopyForTheBookWithTheId(int bookid) throws Throwable {
        Thread.sleep(1000);
        browser.findElement(By.id("field_book")).sendKeys("" + bookid);
    }

    @Then("^a copy has been added$")
    public void aCopyHasBeenAdded() throws Throwable {

    }

    @After
    public void tearDown() throws Exception {
        closeBrowser();
    }
}