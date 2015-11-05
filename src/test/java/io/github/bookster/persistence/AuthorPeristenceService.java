package io.github.bookster.persistence;

import io.github.bookster.Application;
import io.github.bookster.config.MongoConfiguration;
import io.github.bookster.domain.Book;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
public class AuthorPeristenceService {

    @Inject
    private MongoTemplate mongoTemplate;

    @Test
    public void testName() throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("book.$authors").in(new ObjectId("563b768ad4c6ac210c8abed5")));
        System.out.println(mongoTemplate.find(query,Book.class));

    }
}
