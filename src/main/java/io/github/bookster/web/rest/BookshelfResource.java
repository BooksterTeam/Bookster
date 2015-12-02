package io.github.bookster.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.bookster.domain.Book;
import io.github.bookster.domain.Copy;
import io.github.bookster.domain.User;
import io.github.bookster.repository.UserRepository;
import io.github.bookster.repository.book.BookRepository;
import io.github.bookster.repository.CopyRepository;
import io.github.bookster.web.model.CopyModel;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Copy.
 */
@RestController
@RequestMapping("/api/bookshelf")
public class BookshelfResource {

    private final Logger log = LoggerFactory.getLogger(CopyResource.class);

    @Inject
    private CopyRepository copyRepository;

    @Inject
    private BookRepository bookRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /copys -> Create a new copy.
     */
    @RequestMapping(value = "/copys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Copy> addCopy(@RequestBody CopyModel copyModel, Principal principal) throws URISyntaxException {
        log.debug("REST request to save Copy : {}", copyModel);
        UserDetails activeUser =(UserDetails) ((Authentication) principal).getPrincipal();
        log.debug("REST request user to save Copy: {}", activeUser);
        log.info(activeUser.getUsername());
        if (copyModel.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new copy cannot already have an ID").body(null);
        }

        Copy copy = new Copy(copyModel.getAvailable(), copyModel.getVerified());
        //todo https://github.com/BooksterTeam/Bookster/issues/8
        if(copyModel.getBook() != null){
            Book book = bookRepository.findOne(copyModel.getBook());
            copy.setBook(book.getId());
        }
        User user = userRepository.findOneByLogin(activeUser.getUsername()).orElse(null);

        if (user != null) {
            copy.setUser(user.getId());
        }

        Copy result = copyRepository.save(copy);
        return ResponseEntity.created(new URI("/api/copys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("copy", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /copys -> Updates an existing copy.
     */
    @RequestMapping(value = "/copys",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Copy> updateCopy(@RequestBody CopyModel copyModel, Principal principal) throws URISyntaxException {
        log.debug("REST request to update Copy : {}", copyModel);
        if (copyModel.getId() == null) {
            return addCopy(copyModel, principal);
        }

        Copy copy = new Copy(copyModel.getId(), copyModel.getAvailable(), copyModel.getVerified());
        //todo https://github.com/BooksterTeam/Bookster/issues/8
        if(copyModel.getBook() != null){
            Book book = bookRepository.findOne(copyModel.getBook());
            copy.setBook(book.getId());
        }

        Copy result = copyRepository.save(copy);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("copy", copy.getId().toString()))
                .body(result);
    }

    /**
     * GET  /copys -> get all the copys.
     */
    @RequestMapping(value = "/copys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Copy>> getAllCopys(Pageable pageable)
            throws URISyntaxException {
        Page<Copy> page = copyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/copys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /copys/:id -> get the "id" copy.
     */
    @RequestMapping(value = "/copys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Copy> getCopy(@PathVariable String id) {
        log.debug("REST request to get Copy : {}", id);
        return Optional.ofNullable(copyRepository.findOne(id))
                .map(copy -> new ResponseEntity<>(
                        copy,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /copys/:id -> delete the "id" copy.
     */
    @RequestMapping(value = "/copys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCopy(@PathVariable String id) {
        log.debug("REST request to delete Copy : {}", id);
        copyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("copy", id.toString())).build();
    }
}
