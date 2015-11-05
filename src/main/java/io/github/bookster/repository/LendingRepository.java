package io.github.bookster.repository;

import io.github.bookster.domain.Lending;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Lending entity.
 */
public interface LendingRepository extends MongoRepository<Lending,String> {

}
