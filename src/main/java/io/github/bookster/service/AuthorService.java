package io.github.bookster.service;

import io.github.bookster.domain.Author;
import io.github.bookster.domain.Book;
import io.github.bookster.repository.AuthorRepository;
import io.github.bookster.web.model.author.AuthorModel;
import io.github.bookster.web.model.author.BookDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created on 05/11/15
 * author: nixoxo
 */

@Service
public class AuthorService {

    private final Logger log = LoggerFactory.getLogger(AuthorService.class);

    @Inject
    private AuthorRepository authorRepository;


    // Todo https://github.com/BooksterTeam/Bookster/issues/14
    public ResponseEntity<AuthorModel> getAuthor(String id) {
        return Optional.ofNullable(authorRepository.findOne(id)).map(author -> new ResponseEntity<>(parseAuthor(author, authorRepository.findBooks(id)), HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    private AuthorModel parseAuthor(Author author, List<Book> books) {
        return new AuthorModel(author.getId(), author.getForename(), author.getSurname(),
                books.stream().map(book -> new BookDataModel(book.getId(), book.getTitle())).collect(Collectors.toList()));
    }
}
