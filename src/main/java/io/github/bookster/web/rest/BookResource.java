package io.github.bookster.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.bookster.domain.Author;
import io.github.bookster.domain.Book;
import io.github.bookster.domain.Tag;
import io.github.bookster.repository.author.AuthorRepository;
import io.github.bookster.repository.book.BookRepository;
import io.github.bookster.repository.TagRepository;
import io.github.bookster.service.BookService;
import io.github.bookster.web.model.book.BookModel;
import io.github.bookster.web.rest.util.HeaderUtil;
import io.github.bookster.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Book.
 */
@RestController
@RequestMapping("/api")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    @Inject
    private BookRepository bookRepository;

    @Inject
    private TagRepository tagRepository;

    @Inject
    private AuthorRepository authorRepository;

    @Inject
    private BookService bookService;

    /**
     * POST  /books -> Create a new book.
     */
    @RequestMapping(value = "/books",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Book> createBook(@RequestBody BookModel bookModel) throws URISyntaxException {
        log.debug("REST request to save Book : {}", bookModel);
        if (bookModel.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new book cannot already have an ID").body(null);
        }
        Book book = new Book(null, bookModel.getIsbn(), bookModel.getTitle(), bookModel.getVerified(), bookModel.getPublished(), bookModel.getSubtitle());
        if (bookModel.getTag() != null) {
            Tag tag =  tagRepository.findOne(bookModel.getTag());
            book.getTags().add(tag);
        }
        if (bookModel.getAuthor() != null) {
            Author author = authorRepository.findOne(bookModel.getAuthor());
            book.getAuthors().add(author.getId());
        }
        Book result = bookRepository.save(book);
        return ResponseEntity.created(new URI("/api/books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("book", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /books -> Updates an existing book.
     */
    //Todo https://github.com/BooksterTeam/Bookster/issues/4
    @RequestMapping(value = "/books",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Book> updateBook(@RequestBody BookModel bookModel) throws URISyntaxException {
        log.debug("REST request to update Book : {}", bookModel);
        if (bookModel.getId() == null &&  bookRepository.findOne(bookModel.getId())== null) {
            return createBook(bookModel);
        }
        Book book = new Book(bookModel.getId(), bookModel.getIsbn(), bookModel.getTitle(), bookModel.getVerified(), bookModel.getPublished(), bookModel.getSubtitle());
        if (bookModel.getAuthor() != null) {
            Author author = authorRepository.findOne(bookModel.getAuthor());
            book.getAuthors().add(author.getId());
        }
        Book result = bookRepository.save(book);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("book", bookModel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /books -> get all the books.
     */
    @RequestMapping(value = "/books",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Book>> getAllBooks(Pageable pageable)
        throws URISyntaxException {
        Page<Book> page = bookRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/books");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /books/:id -> get the "id" book.
     */
    @RequestMapping(value = "/books/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BookModel> getBook(@PathVariable String id) {
        log.debug("REST request to get Book : {}", id);
        return Optional.ofNullable(bookService.getBook(id)).map(bookModel -> new ResponseEntity<>(bookModel, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /books/:id -> delete the "id" book.
     */
    @RequestMapping(value = "/books/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        log.debug("REST request to delete Book : {}", id);
        bookRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("book", id.toString())).build();
    }
}
