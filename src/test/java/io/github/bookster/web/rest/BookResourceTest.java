package io.github.bookster.web.rest;

import io.github.bookster.Application;
import io.github.bookster.domain.Book;
import io.github.bookster.repository.book.BookRepository;
import io.github.bookster.web.model.book.BookModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BookResource REST controller.
 *
 * @see BookResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BookResourceTest {

    private static final String DEFAULT_ISBN = "AAAAA";
    private static final String UPDATED_ISBN = "BBBBB";
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final Boolean DEFAULT_VERIFIED = false;
    private static final Boolean UPDATED_VERIFIED = true;

    private static final LocalDate DEFAULT_PUBLISHED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLISHED = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_SUBTITLE = "AAAAA";
    private static final String UPDATED_SUBTITLE = "BBBBB";

    @Inject
    private BookRepository bookRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBookMockMvc;

    private BookModel bookModel;
    private Book book;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookResource bookResource = new BookResource();
        ReflectionTestUtils.setField(bookResource, "bookRepository", bookRepository);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bookRepository.deleteAll();
        bookModel = new BookModel();
        bookModel.setIsbn(DEFAULT_ISBN);
        bookModel.setTitle(DEFAULT_TITLE);
        bookModel.setVerified(DEFAULT_VERIFIED);
        //bookModel.setPublished(DEFAULT_PUBLISHED);
        bookModel.setSubtitle(DEFAULT_SUBTITLE);

        book = new Book();
        book.setIsbn(DEFAULT_ISBN);
        book.setTitle(DEFAULT_TITLE);
        book.setVerified(DEFAULT_VERIFIED);
        //book.setPublished(DEFAULT_PUBLISHED);
        book.setSubtitle(DEFAULT_SUBTITLE);
    }

    @Test
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookModel)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = books.get(books.size() - 1);
        assertThat(testBook.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getVerified()).isEqualTo(DEFAULT_VERIFIED);

        //Todo https://github.com/BooksterTeam/Bookster/issues/4
        //assertThat(testBook.getPublished()).isEqualTo(DEFAULT_PUBLISHED);
        assertThat(testBook.getSubtitle()).isEqualTo(DEFAULT_SUBTITLE);
    }

    @Test
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.save(book);

        // Get all the books
        restBookMockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].subtitle").value(hasItem(DEFAULT_SUBTITLE.toString())));

        //Todo https://github.com/BooksterTeam/Bookster/issues/4
        //.andExpect(jsonPath("$.[*].published").value(hasItem(DEFAULT_PUBLISHED.toString())))
    }

    @Test
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.save(book);

        // Get the bookModel
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(book.getId()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.subtitle").value(DEFAULT_SUBTITLE.toString()));

        //Todo https://github.com/BooksterTeam/Bookster/issues/4
        //.andExpect(jsonPath("$.published").value(DEFAULT_PUBLISHED.toString()))
    }

    @Test
    public void getNonExistingBook() throws Exception {
        // Get the bookModel
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBook() throws Exception {
        // Initialize the database
        bookRepository.save(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the bookModel
        bookModel.setId(book.getId());
        bookModel.setIsbn(UPDATED_ISBN);
        bookModel.setTitle(UPDATED_TITLE);
        bookModel.setVerified(UPDATED_VERIFIED);

        //Todo https://github.com/BooksterTeam/Bookster/issues/4
        //bookModel.setPublished(UPDATED_PUBLISHED);
        bookModel.setSubtitle(UPDATED_SUBTITLE);

        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookModel)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeUpdate);
        Book testBook = books.get(books.size() - 1);
        assertThat(testBook.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testBook.getSubtitle()).isEqualTo(UPDATED_SUBTITLE);

        //Todo https://github.com/BooksterTeam/Bookster/issues/4
        //assertThat(testBook.getPublished()).isEqualTo(UPDATED_PUBLISHED);
    }

    @Test
    public void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.save(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Get the bookModel
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeDelete - 1);
    }
}
