package io.github.bookster.repository.author;

import io.github.bookster.domain.Book;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

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
        query.addCriteria(where("authors").in(id));
        return Optional.ofNullable(mongoTemplate.find(query, Book.class)).orElse(null);
    }
}
