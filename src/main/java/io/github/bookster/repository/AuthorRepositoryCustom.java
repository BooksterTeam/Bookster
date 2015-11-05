package io.github.bookster.repository;

import io.github.bookster.domain.Book;

import java.util.List;

/**
 * Created on 05/11/15
 * author: nixoxo
 */
public interface AuthorRepositoryCustom {

    List<Book> findBooks(String id);
}
