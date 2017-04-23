package com.github.xxbeanxx.mymerit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.xxbeanxx.mymerit.service.MyMeritUserService;
import com.github.xxbeanxx.mymerit.web.rest.util.HeaderUtil;
import com.github.xxbeanxx.mymerit.service.dto.MyMeritUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing MyMeritUser.
 */
@RestController
@RequestMapping("/api")
public class MyMeritUserResource {

    private final Logger log = LoggerFactory.getLogger(MyMeritUserResource.class);

    private static final String ENTITY_NAME = "myMeritUser";
        
    private final MyMeritUserService myMeritUserService;

    public MyMeritUserResource(MyMeritUserService myMeritUserService) {
        this.myMeritUserService = myMeritUserService;
    }

    /**
     * POST  /my-merit-users : Create a new myMeritUser.
     *
     * @param myMeritUserDTO the myMeritUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myMeritUserDTO, or with status 400 (Bad Request) if the myMeritUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-merit-users")
    @Timed
    public ResponseEntity<MyMeritUserDTO> createMyMeritUser(@RequestBody MyMeritUserDTO myMeritUserDTO) throws URISyntaxException {
        log.debug("REST request to save MyMeritUser : {}", myMeritUserDTO);
        if (myMeritUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new myMeritUser cannot already have an ID")).body(null);
        }
        MyMeritUserDTO result = myMeritUserService.save(myMeritUserDTO);
        return ResponseEntity.created(new URI("/api/my-merit-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-merit-users : Updates an existing myMeritUser.
     *
     * @param myMeritUserDTO the myMeritUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myMeritUserDTO,
     * or with status 400 (Bad Request) if the myMeritUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the myMeritUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-merit-users")
    @Timed
    public ResponseEntity<MyMeritUserDTO> updateMyMeritUser(@RequestBody MyMeritUserDTO myMeritUserDTO) throws URISyntaxException {
        log.debug("REST request to update MyMeritUser : {}", myMeritUserDTO);
        if (myMeritUserDTO.getId() == null) {
            return createMyMeritUser(myMeritUserDTO);
        }
        MyMeritUserDTO result = myMeritUserService.save(myMeritUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, myMeritUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-merit-users : get all the myMeritUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myMeritUsers in body
     */
    @GetMapping("/my-merit-users")
    @Timed
    public List<MyMeritUserDTO> getAllMyMeritUsers() {
        log.debug("REST request to get all MyMeritUsers");
        return myMeritUserService.findAll();
    }

    /**
     * GET  /my-merit-users/:id : get the "id" myMeritUser.
     *
     * @param id the id of the myMeritUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myMeritUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/my-merit-users/{id}")
    @Timed
    public ResponseEntity<MyMeritUserDTO> getMyMeritUser(@PathVariable Long id) {
        log.debug("REST request to get MyMeritUser : {}", id);
        MyMeritUserDTO myMeritUserDTO = myMeritUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(myMeritUserDTO));
    }

    /**
     * DELETE  /my-merit-users/:id : delete the "id" myMeritUser.
     *
     * @param id the id of the myMeritUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-merit-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyMeritUser(@PathVariable Long id) {
        log.debug("REST request to delete MyMeritUser : {}", id);
        myMeritUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
