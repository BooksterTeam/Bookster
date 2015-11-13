package io.github.bookster.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import io.github.bookster.domain.Book;
import io.github.bookster.repository.book.BookRepository;
import io.github.bookster.service.BookService;
import io.github.bookster.web.rest.BookResource;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BookDetailsStepDefs extends BaseIntegration {

    @Inject
    private BookRepository bookRepository;

    @Inject
    private BookService bookService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBookMockMvc;

    private ResultActions successAction;

    private ResultActions failedAction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookResource bookResource = new BookResource();
        ReflectionTestUtils.setField(bookResource, "bookRepository", bookRepository);
        ReflectionTestUtils.setField(bookResource, "bookService", bookService);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Given("^book is in database$")
    public void book_is_in_database() throws Throwable {
        Book book = new Book("book", "isbn", "title", true, "published", "subtitle");
        bookRepository.insert(book);
    }

    @When("^user lookup a book which does not exist with id '(.*)'$")
    public void user_lookup_a_book_with_id_notindatabase(String bookid) throws Throwable {
        failedAction = restBookMockMvc.perform(get("/api/books/" + bookid).accept(MediaType.APPLICATION_JSON));
    }

    @When("^user lookup a book with id '(.*)'$")
    public void user_lookup_a_book_with_id_1(String bookid) throws Throwable {
        successAction = restBookMockMvc.perform(get("/api/books/" + bookid).accept(MediaType.APPLICATION_JSON));
    }

    @Then("^the book is found$")
    public void the_book_is_found() throws Throwable {
        successAction.andExpect(status().isOk());
    }
    @Then("^the title of the book is '(.*)'$")
    public void the_title_of_the_book_is_something(String title) throws Throwable {
        successAction.andExpect(jsonPath("$.title").value(title));
    }

    @Then("^the isbn of the book is '(.*)'$")
    public void the_isbn_of_the_book_is_something(String isbn) throws Throwable {
        successAction.andExpect(jsonPath("$.isbn").value(isbn));
    }

    @Then("^then the status is 404$")
    public void then_the_status_is_404() throws Throwable {
        failedAction.andExpect(status().is(404));
    }
}