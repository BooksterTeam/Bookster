package io.github.bookster.persistence;

import io.github.bookster.Application;
import io.github.bookster.config.MongoConfiguration;
import io.github.bookster.domain.Author;
import io.github.bookster.domain.Book;
import io.github.bookster.repository.author.AuthorRepository;
import io.github.bookster.repository.book.BookRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.junit.Assert.assertThat;

/**
 * Created on 05/11/15
 * author: nixoxo
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Import(MongoConfiguration.class)
public class AuthorPeristenceService {

    @Inject
    private AuthorRepository authorRepository;

    @Inject
    private BookRepository bookRepository;

    private String author1Id;
    private String author2Id;


    @Before
    public void setUp() throws Exception {

        Author author = new Author("test", "test");
        authorRepository.save(author);
        author1Id = author.getId();

        Author author2 = new Author("test1", "test1");
        author2Id = author2.getId();

        Book book1 = new Book();
        book1.getAuthors().add(author1Id);
        book1.getAuthors().add(author2Id);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.getAuthors().add(author1Id);
        bookRepository.save(book2);
    }

    @Test
    public void lookupForAuthorIdInBooks() throws Exception {
        assertThat(authorRepository.findBooks(author1Id).size(), Matchers.is(2));
        assertThat(authorRepository.findBooks(author2Id).size(), Matchers.is(1));
    }
}
