package io.github.bookster.service;

import io.github.bookster.domain.Book;
import io.github.bookster.domain.Copy;
import io.github.bookster.repository.book.BookRepository;
import io.github.bookster.web.model.book.AuthorDataModel;
import io.github.bookster.web.model.book.BookModel;
import io.github.bookster.web.model.book.CopyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    @Inject
    private BookRepository bookRepository;

    // Todo https://github.com/BooksterTeam/Bookster/issues/14
    public BookModel getBook(String id) {
        log.info("________________________");
        log.info("GETTING");
        return Optional.ofNullable(bookRepository.findOne(id)).map(book -> parseBook(book, bookRepository.findCopies(id))).orElse(null);
    }

    private BookModel parseBook(Book book, List<Copy> copies) {
        log.info("________________________");
        log.info("PARSING");
        return new BookModel(book.getId(), book.getIsbn(), book.getTitle(), Optional.ofNullable(book.getPublished()).orElse(""), Optional.ofNullable(book.getSubtitle()).orElse(""), "IMPLEMENT ME", book.getAuthors().stream().map(author -> new AuthorDataModel(author.getId(), author.getForename() + author.getSurname())).collect(Collectors.toList()), copies.stream().map(copy -> new CopyDataModel(copy.getId(), Optional.ofNullable(copy.getAvailable()).orElse(false))).collect(Collectors.toList()));
    }
}
