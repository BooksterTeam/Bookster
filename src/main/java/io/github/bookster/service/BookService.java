package io.github.bookster.service;

import io.github.bookster.domain.Book;
import io.github.bookster.domain.Copy;
import io.github.bookster.repository.author.AuthorRepository;
import io.github.bookster.repository.book.BookRepository;
import io.github.bookster.web.model.book.AuthorDataModel;
import io.github.bookster.web.model.book.BookModel;
import io.github.bookster.web.model.book.CopyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * Created on 05/11/15
 * author: nixoxo
 */

@Service
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    @Inject
    private BookRepository bookRepository;

    @Inject
    private AuthorRepository authorRepository;

    // Todo https://github.com/BooksterTeam/Bookster/issues/14
    public BookModel getBook(String id) {
        return ofNullable(bookRepository.findOne(id)).map(book -> parseBook(book, bookRepository.findCopies(id))).orElse(null);
    }

    private BookModel parseBook(Book book, List<Copy> copies) {
        return new BookModel(book.getId(), book.getIsbn(), book.getTitle(), ofNullable(book.getPublished()).orElse(""), ofNullable(book.getSubtitle()).orElse(""), "IMPLEMENT ME", book.getAuthors().stream().map(id ->
                ofNullable(authorRepository.findOne(id)).map(author -> new AuthorDataModel(author.getId(), author.getForename() + " " + author.getSurname())).orElse(new AuthorDataModel()))
                .collect(Collectors.toList()), copies.stream().map(copy -> new CopyDataModel(copy.getId(), ofNullable(copy.getAvailable()).orElse(false))).collect(Collectors.toList()));
    }
}
