package io.github.bookster.repository.book;

import io.github.bookster.domain.Copy;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created on 05/11/15
 * author: nixoxo
 */

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    @Inject
    private MongoTemplate mongoTemplate;

    @Override
    public List<Copy> findCopies(String id) {
        Query query = new Query();
        try {
            query.addCriteria(Criteria.where("book.$id").is(new ObjectId(id)));
            return Optional.ofNullable(mongoTemplate.find(query, Copy.class)).orElse(null);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
