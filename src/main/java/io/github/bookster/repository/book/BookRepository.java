package io.github.bookster.repository.book;

import io.github.bookster.domain.Book;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Book entity.
 */
public interface BookRepository extends MongoRepository<Book,String>, BookRepositoryCustom {

}
