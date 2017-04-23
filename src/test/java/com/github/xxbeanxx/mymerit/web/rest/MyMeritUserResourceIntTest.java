package com.github.xxbeanxx.mymerit.web.rest;

import com.github.xxbeanxx.mymerit.MymeritApp;

import com.github.xxbeanxx.mymerit.domain.MyMeritUser;
import com.github.xxbeanxx.mymerit.repository.MyMeritUserRepository;
import com.github.xxbeanxx.mymerit.service.MyMeritUserService;
import com.github.xxbeanxx.mymerit.service.dto.MyMeritUserDTO;
import com.github.xxbeanxx.mymerit.service.mapper.MyMeritUserMapper;
import com.github.xxbeanxx.mymerit.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MyMeritUserResource REST controller.
 *
 * @see MyMeritUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MymeritApp.class)
public class MyMeritUserResourceIntTest {

    @Autowired
    private MyMeritUserRepository myMeritUserRepository;

    @Autowired
    private MyMeritUserMapper myMeritUserMapper;

    @Autowired
    private MyMeritUserService myMeritUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyMeritUserMockMvc;

    private MyMeritUser myMeritUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyMeritUserResource myMeritUserResource = new MyMeritUserResource(myMeritUserService);
        this.restMyMeritUserMockMvc = MockMvcBuilders.standaloneSetup(myMeritUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyMeritUser createEntity(EntityManager em) {
        MyMeritUser myMeritUser = new MyMeritUser();
        return myMeritUser;
    }

    @Before
    public void initTest() {
        myMeritUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyMeritUser() throws Exception {
        int databaseSizeBeforeCreate = myMeritUserRepository.findAll().size();

        // Create the MyMeritUser
        MyMeritUserDTO myMeritUserDTO = myMeritUserMapper.myMeritUserToMyMeritUserDTO(myMeritUser);
        restMyMeritUserMockMvc.perform(post("/api/my-merit-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myMeritUserDTO)))
            .andExpect(status().isCreated());

        // Validate the MyMeritUser in the database
        List<MyMeritUser> myMeritUserList = myMeritUserRepository.findAll();
        assertThat(myMeritUserList).hasSize(databaseSizeBeforeCreate + 1);
        MyMeritUser testMyMeritUser = myMeritUserList.get(myMeritUserList.size() - 1);
    }

    @Test
    @Transactional
    public void createMyMeritUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myMeritUserRepository.findAll().size();

        // Create the MyMeritUser with an existing ID
        myMeritUser.setId(1L);
        MyMeritUserDTO myMeritUserDTO = myMeritUserMapper.myMeritUserToMyMeritUserDTO(myMeritUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyMeritUserMockMvc.perform(post("/api/my-merit-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myMeritUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MyMeritUser> myMeritUserList = myMeritUserRepository.findAll();
        assertThat(myMeritUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMyMeritUsers() throws Exception {
        // Initialize the database
        myMeritUserRepository.saveAndFlush(myMeritUser);

        // Get all the myMeritUserList
        restMyMeritUserMockMvc.perform(get("/api/my-merit-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myMeritUser.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMyMeritUser() throws Exception {
        // Initialize the database
        myMeritUserRepository.saveAndFlush(myMeritUser);

        // Get the myMeritUser
        restMyMeritUserMockMvc.perform(get("/api/my-merit-users/{id}", myMeritUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myMeritUser.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMyMeritUser() throws Exception {
        // Get the myMeritUser
        restMyMeritUserMockMvc.perform(get("/api/my-merit-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyMeritUser() throws Exception {
        // Initialize the database
        myMeritUserRepository.saveAndFlush(myMeritUser);
        int databaseSizeBeforeUpdate = myMeritUserRepository.findAll().size();

        // Update the myMeritUser
        MyMeritUser updatedMyMeritUser = myMeritUserRepository.findOne(myMeritUser.getId());
        MyMeritUserDTO myMeritUserDTO = myMeritUserMapper.myMeritUserToMyMeritUserDTO(updatedMyMeritUser);

        restMyMeritUserMockMvc.perform(put("/api/my-merit-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myMeritUserDTO)))
            .andExpect(status().isOk());

        // Validate the MyMeritUser in the database
        List<MyMeritUser> myMeritUserList = myMeritUserRepository.findAll();
        assertThat(myMeritUserList).hasSize(databaseSizeBeforeUpdate);
        MyMeritUser testMyMeritUser = myMeritUserList.get(myMeritUserList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMyMeritUser() throws Exception {
        int databaseSizeBeforeUpdate = myMeritUserRepository.findAll().size();

        // Create the MyMeritUser
        MyMeritUserDTO myMeritUserDTO = myMeritUserMapper.myMeritUserToMyMeritUserDTO(myMeritUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyMeritUserMockMvc.perform(put("/api/my-merit-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myMeritUserDTO)))
            .andExpect(status().isCreated());

        // Validate the MyMeritUser in the database
        List<MyMeritUser> myMeritUserList = myMeritUserRepository.findAll();
        assertThat(myMeritUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMyMeritUser() throws Exception {
        // Initialize the database
        myMeritUserRepository.saveAndFlush(myMeritUser);
        int databaseSizeBeforeDelete = myMeritUserRepository.findAll().size();

        // Get the myMeritUser
        restMyMeritUserMockMvc.perform(delete("/api/my-merit-users/{id}", myMeritUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MyMeritUser> myMeritUserList = myMeritUserRepository.findAll();
        assertThat(myMeritUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyMeritUser.class);
    }
}
