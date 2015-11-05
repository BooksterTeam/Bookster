package io.github.bookster.persistence;

import io.github.bookster.Application;
import io.github.bookster.config.MongoConfiguration;
import io.github.bookster.domain.Book;
import io.github.bookster.domain.Copy;
import io.github.bookster.repository.CopyRepository;
import io.github.bookster.repository.book.BookRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

/**
 * Created on 05/11/15
 * author: nixoxo
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Import(MongoConfiguration.class)
public class BookPersistenceService {

    @Inject
    private CopyRepository copyRepository;

    @Inject
    private BookRepository bookRepository;

    private String bookId;

    @Before
    public void setUp() throws Exception {

        Book book = new Book();
        bookRepository.save(book);
        bookId = book.getId();

        Copy copy = new Copy(true, true);
        copy.setBook(book);
        copyRepository.save(copy);
    }

    @Test
    public void lookUpCopiesOfBook() throws Exception {
        Assert.assertThat(bookRepository.findCopies(bookId).size(), Matchers.is(1));

    }
}
