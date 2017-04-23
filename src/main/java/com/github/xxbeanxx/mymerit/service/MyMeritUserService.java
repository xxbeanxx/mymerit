package com.github.xxbeanxx.mymerit.service;

import com.github.xxbeanxx.mymerit.service.dto.MyMeritUserDTO;
import java.util.List;

/**
 * Service Interface for managing MyMeritUser.
 */
public interface MyMeritUserService {

    /**
     * Save a myMeritUser.
     *
     * @param myMeritUserDTO the entity to save
     * @return the persisted entity
     */
    MyMeritUserDTO save(MyMeritUserDTO myMeritUserDTO);

    /**
     *  Get all the myMeritUsers.
     *  
     *  @return the list of entities
     */
    List<MyMeritUserDTO> findAll();

    /**
     *  Get the "id" myMeritUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MyMeritUserDTO findOne(Long id);

    /**
     *  Delete the "id" myMeritUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
