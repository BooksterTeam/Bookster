package io.github.bookster.cucumber;

import io.github.bookster.Application;
import io.github.bookster.config.MongoConfiguration;
import io.github.bookster.repository.book.BookRepository;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

/**
 * Created on 13/11/15
 * author: nixoxo
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Import(MongoConfiguration.class)
public class BaseIntegration {

    @Inject
    protected BookRepository bookRepository;
}
