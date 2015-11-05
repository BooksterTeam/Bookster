package io.github.bookster.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.bookster.domain.Author;
import io.github.bookster.repository.AuthorRepository;
import io.github.bookster.repository.BookRepository;
import io.github.bookster.service.AuthorService;
import io.github.bookster.web.model.author.AuthorModel;
import io.github.bookster.web.rest.util.HeaderUtil;
import io.github.bookster.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
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
 * REST controller for managing Author.
 */
@RestController
@RequestMapping("/api")
public class AuthorResource {

    private final Logger log = LoggerFactory.getLogger(AuthorResource.class);

    @Inject
    private AuthorRepository authorRepository;

    @Inject
    private AuthorService authorService;

    /**
     * POST  /authors -> Create a new author.
     */
    @RequestMapping(value = "/authors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorModel authorModel) throws URISyntaxException {
        log.debug("REST request to save Author : {}", authorModel);
        if (authorModel.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new author cannot already have an ID").body(null);
        }

        Author author = new Author(authorModel.getForename(), authorModel.getSurname());

        Author result = authorRepository.save(author);
        return ResponseEntity.created(new URI("/api/authors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("author", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authors -> Updates an existing author.
     */
    @RequestMapping(value = "/authors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Author> updateAuthor(@RequestBody AuthorModel authorModel) throws URISyntaxException {
        log.debug("REST request to update Author : {}", authorModel);
        if (authorModel.getId() == null) {
            return createAuthor(authorModel);
        }
        Author author = new Author(authorModel.getId(), authorModel.getForename(), authorModel.getSurname());

        Author result = authorRepository.save(author);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("author", author.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authors -> get all the authors.
     */
    @RequestMapping(value = "/authors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Author>> getAllAuthors(Pageable pageable)
        throws URISyntaxException {
        Page<Author> page = authorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /authors/:id -> get the "id" author.
     */
    @RequestMapping(value = "/authors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorModel> getAuthor(@PathVariable String id) {
        log.debug("REST request to get Author : {}", id);
        return authorService.getAuthor(id);
    }

    /**
     * DELETE  /authors/:id -> delete the "id" author.
     */
    @RequestMapping(value = "/authors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        log.debug("REST request to delete Author : {}", id);
        authorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("author", id.toString())).build();
    }
}
