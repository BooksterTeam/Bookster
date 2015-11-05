package io.github.bookster.repository;

import io.github.bookster.domain.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created on 05/11/15
 * author: nixoxo
 */

@Repository
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    @Inject
    private MongoTemplate mongoTemplate;

    @Override
    public List<Book> findBooks(String id) {
        Query query = new Query();
        List<Book> books = mongoTemplate.findAll(Book.class);
        query.addCriteria(Criteria.where("authors.$id").is(new ObjectId(id)));
        return mongoTemplate.find(query, Book.class);
    }
}
