package io.github.bookster.repository;

import io.github.bookster.domain.Copy;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Copy entity.
 */
public interface CopyRepository extends MongoRepository<Copy,String> {

}
