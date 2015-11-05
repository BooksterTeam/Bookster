package io.github.bookster.repository.book;

import io.github.bookster.domain.Copy;

import java.util.List;

/**
 * Created on 05/11/15
 * author: nixoxo
 */
public interface BookRepositoryCustom {

    List<Copy> findCopies(String id);
}
