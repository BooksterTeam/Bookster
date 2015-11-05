package io.github.bookster.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.bookster.domain.Copy;
import io.github.bookster.domain.Lending;
import io.github.bookster.domain.User;
import io.github.bookster.repository.CopyRepository;
import io.github.bookster.repository.LendingRepository;
import io.github.bookster.repository.UserRepository;
import io.github.bookster.web.model.LendingModel;
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
 * REST controller for managing Lending.
 */
@RestController
@RequestMapping("/api")
public class LendingResource {

    private final Logger log = LoggerFactory.getLogger(LendingResource.class);

    @Inject
    private LendingRepository lendingRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private CopyRepository copyRepository;

    /**
     * POST  /lendings -> Create a new lending.
     */
    @RequestMapping(value = "/lendings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lending> createLending(@RequestBody LendingModel lendingModel) throws URISyntaxException {
        log.debug("REST request to save Lending : {}", lendingModel);
        if (lendingModel.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new lending cannot already have an ID").body(null);
        }
        Lending lending = new Lending(lendingModel.getFrom(), lendingModel.getDue());

        if (lendingModel.getCopi() != null) {
            Copy copy = copyRepository.findOne(lendingModel.getCopi());
            lending.setCopy(copy);
        }
        if (lendingModel.getUser() != null) {
            User user= userRepository.findOne(lendingModel.getUser());
            lending.setUser(user);
        }
        Lending result = lendingRepository.save(lending);
        return ResponseEntity.created(new URI("/api/lendings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lending", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lendings -> Updates an existing lending.
     */
    @RequestMapping(value = "/lendings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lending> updateLending(@RequestBody LendingModel lendingModel) throws URISyntaxException {
        log.debug("REST request to update Lending : {}", lendingModel);
        if (lendingModel.getId() == null) {
            return createLending(lendingModel);
        }

        Lending lending = new Lending(lendingModel.getId(),lendingModel.getFrom(), lendingModel.getDue());

        if (lendingModel.getCopi() != null) {
            Copy copy = copyRepository.findOne(lendingModel.getCopi());
            lending.setCopy(copy);
        }
        if (lendingModel.getUser() != null) {
            User user= userRepository.findOne(lendingModel.getUser());
            lending.setUser(user);
        }
        Lending result = lendingRepository.save(lending);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lending", lending.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lendings -> get all the lendings.
     */
    @RequestMapping(value = "/lendings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Lending>> getAllLendings(Pageable pageable)
        throws URISyntaxException {
        Page<Lending> page = lendingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lendings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lendings/:id -> get the "id" lending.
     */
    @RequestMapping(value = "/lendings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lending> getLending(@PathVariable String id) {
        log.debug("REST request to get Lending : {}", id);
        return Optional.ofNullable(lendingRepository.findOne(id))
            .map(lending -> new ResponseEntity<>(
                lending,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lendings/:id -> delete the "id" lending.
     */
    @RequestMapping(value = "/lendings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLending(@PathVariable String id) {
        log.debug("REST request to delete Lending : {}", id);
        lendingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lending", id.toString())).build();
    }
}
