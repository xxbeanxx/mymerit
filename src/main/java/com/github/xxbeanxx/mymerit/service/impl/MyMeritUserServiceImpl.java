package com.github.xxbeanxx.mymerit.service.impl;

import com.github.xxbeanxx.mymerit.service.MyMeritUserService;
import com.github.xxbeanxx.mymerit.domain.MyMeritUser;
import com.github.xxbeanxx.mymerit.repository.MyMeritUserRepository;
import com.github.xxbeanxx.mymerit.service.dto.MyMeritUserDTO;
import com.github.xxbeanxx.mymerit.service.mapper.MyMeritUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MyMeritUser.
 */
@Service
@Transactional
public class MyMeritUserServiceImpl implements MyMeritUserService{

    private final Logger log = LoggerFactory.getLogger(MyMeritUserServiceImpl.class);
    
    private final MyMeritUserRepository myMeritUserRepository;

    private final MyMeritUserMapper myMeritUserMapper;

    public MyMeritUserServiceImpl(MyMeritUserRepository myMeritUserRepository, MyMeritUserMapper myMeritUserMapper) {
        this.myMeritUserRepository = myMeritUserRepository;
        this.myMeritUserMapper = myMeritUserMapper;
    }

    /**
     * Save a myMeritUser.
     *
     * @param myMeritUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MyMeritUserDTO save(MyMeritUserDTO myMeritUserDTO) {
        log.debug("Request to save MyMeritUser : {}", myMeritUserDTO);
        MyMeritUser myMeritUser = myMeritUserMapper.myMeritUserDTOToMyMeritUser(myMeritUserDTO);
        myMeritUser = myMeritUserRepository.save(myMeritUser);
        MyMeritUserDTO result = myMeritUserMapper.myMeritUserToMyMeritUserDTO(myMeritUser);
        return result;
    }

    /**
     *  Get all the myMeritUsers.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MyMeritUserDTO> findAll() {
        log.debug("Request to get all MyMeritUsers");
        List<MyMeritUserDTO> result = myMeritUserRepository.findAll().stream()
            .map(myMeritUserMapper::myMeritUserToMyMeritUserDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one myMeritUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MyMeritUserDTO findOne(Long id) {
        log.debug("Request to get MyMeritUser : {}", id);
        MyMeritUser myMeritUser = myMeritUserRepository.findOne(id);
        MyMeritUserDTO myMeritUserDTO = myMeritUserMapper.myMeritUserToMyMeritUserDTO(myMeritUser);
        return myMeritUserDTO;
    }

    /**
     *  Delete the  myMeritUser by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyMeritUser : {}", id);
        myMeritUserRepository.delete(id);
    }
}
