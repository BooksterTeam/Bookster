package io.github.bookster.repository;

import io.github.bookster.domain.Author;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Author entity.
 */
public interface AuthorRepository extends MongoRepository<Author,String>, AuthorRepositoryCustom {

}
