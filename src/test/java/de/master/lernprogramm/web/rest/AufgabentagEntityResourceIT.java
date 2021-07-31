package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.LernprogrammApp;
import de.master.lernprogramm.repository.entity.AufgabentagEntity;
import de.master.lernprogramm.repository.AufgabentagEntityRepository;
import de.master.lernprogramm.repository.search.AufgabentagEntitySearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AufgabentagEntityResource} REST controller.
 */
@SpringBootTest(classes = LernprogrammApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AufgabentagEntityResourceIT {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private AufgabentagEntityRepository aufgabentagEntityRepository;

    /**
     * This repository is mocked in the de.master.lernprogramm.repository.search test package.
     *
     * @see de.master.lernprogramm.repository.search.AufgabentagEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private AufgabentagEntitySearchRepository mockAufgabentagEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAufgabentagEntityMockMvc;

    private AufgabentagEntity aufgabentagEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AufgabentagEntity createEntity(EntityManager em) {
        AufgabentagEntity aufgabentagEntity = new AufgabentagEntity()
            .tag(DEFAULT_TAG)
            .text(DEFAULT_TEXT);
        return aufgabentagEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AufgabentagEntity createUpdatedEntity(EntityManager em) {
        AufgabentagEntity aufgabentagEntity = new AufgabentagEntity()
            .tag(UPDATED_TAG)
            .text(UPDATED_TEXT);
        return aufgabentagEntity;
    }

    @BeforeEach
    public void initTest() {
        aufgabentagEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAufgabentagEntity() throws Exception {
        int databaseSizeBeforeCreate = aufgabentagEntityRepository.findAll().size();
        // Create the AufgabentagEntity
        restAufgabentagEntityMockMvc.perform(post("/api/aufgabentag-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabentagEntity)))
            .andExpect(status().isCreated());

        // Validate the AufgabentagEntity in the database
        List<AufgabentagEntity> aufgabentagEntityList = aufgabentagEntityRepository.findAll();
        assertThat(aufgabentagEntityList).hasSize(databaseSizeBeforeCreate + 1);
        AufgabentagEntity testAufgabentagEntity = aufgabentagEntityList.get(aufgabentagEntityList.size() - 1);
        assertThat(testAufgabentagEntity.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testAufgabentagEntity.getText()).isEqualTo(DEFAULT_TEXT);

        // Validate the AufgabentagEntity in Elasticsearch
        verify(mockAufgabentagEntitySearchRepository, times(1)).save(testAufgabentagEntity);
    }

    @Test
    @Transactional
    public void createAufgabentagEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aufgabentagEntityRepository.findAll().size();

        // Create the AufgabentagEntity with an existing ID
        aufgabentagEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAufgabentagEntityMockMvc.perform(post("/api/aufgabentag-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabentagEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AufgabentagEntity in the database
        List<AufgabentagEntity> aufgabentagEntityList = aufgabentagEntityRepository.findAll();
        assertThat(aufgabentagEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the AufgabentagEntity in Elasticsearch
        verify(mockAufgabentagEntitySearchRepository, times(0)).save(aufgabentagEntity);
    }


    @Test
    @Transactional
    public void checkTagIsRequired() throws Exception {
        int databaseSizeBeforeTest = aufgabentagEntityRepository.findAll().size();
        // set the field null
        aufgabentagEntity.setTag(null);

        // Create the AufgabentagEntity, which fails.


        restAufgabentagEntityMockMvc.perform(post("/api/aufgabentag-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabentagEntity)))
            .andExpect(status().isBadRequest());

        List<AufgabentagEntity> aufgabentagEntityList = aufgabentagEntityRepository.findAll();
        assertThat(aufgabentagEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAufgabentagEntities() throws Exception {
        // Initialize the database
        aufgabentagEntityRepository.saveAndFlush(aufgabentagEntity);

        // Get all the aufgabentagEntityList
        restAufgabentagEntityMockMvc.perform(get("/api/aufgabentag-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aufgabentagEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }

    @Test
    @Transactional
    public void getAufgabentagEntity() throws Exception {
        // Initialize the database
        aufgabentagEntityRepository.saveAndFlush(aufgabentagEntity);

        // Get the aufgabentagEntity
        restAufgabentagEntityMockMvc.perform(get("/api/aufgabentag-entities/{id}", aufgabentagEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aufgabentagEntity.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }
    @Test
    @Transactional
    public void getNonExistingAufgabentagEntity() throws Exception {
        // Get the aufgabentagEntity
        restAufgabentagEntityMockMvc.perform(get("/api/aufgabentag-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAufgabentagEntity() throws Exception {
        // Initialize the database
        aufgabentagEntityRepository.saveAndFlush(aufgabentagEntity);

        int databaseSizeBeforeUpdate = aufgabentagEntityRepository.findAll().size();

        // Update the aufgabentagEntity
        AufgabentagEntity updatedAufgabentagEntity = aufgabentagEntityRepository.findById(aufgabentagEntity.getId()).get();
        // Disconnect from session so that the updates on updatedAufgabentagEntity are not directly saved in db
        em.detach(updatedAufgabentagEntity);
        updatedAufgabentagEntity
            .tag(UPDATED_TAG)
            .text(UPDATED_TEXT);

        restAufgabentagEntityMockMvc.perform(put("/api/aufgabentag-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAufgabentagEntity)))
            .andExpect(status().isOk());

        // Validate the AufgabentagEntity in the database
        List<AufgabentagEntity> aufgabentagEntityList = aufgabentagEntityRepository.findAll();
        assertThat(aufgabentagEntityList).hasSize(databaseSizeBeforeUpdate);
        AufgabentagEntity testAufgabentagEntity = aufgabentagEntityList.get(aufgabentagEntityList.size() - 1);
        assertThat(testAufgabentagEntity.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testAufgabentagEntity.getText()).isEqualTo(UPDATED_TEXT);

        // Validate the AufgabentagEntity in Elasticsearch
        verify(mockAufgabentagEntitySearchRepository, times(1)).save(testAufgabentagEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingAufgabentagEntity() throws Exception {
        int databaseSizeBeforeUpdate = aufgabentagEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAufgabentagEntityMockMvc.perform(put("/api/aufgabentag-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabentagEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AufgabentagEntity in the database
        List<AufgabentagEntity> aufgabentagEntityList = aufgabentagEntityRepository.findAll();
        assertThat(aufgabentagEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AufgabentagEntity in Elasticsearch
        verify(mockAufgabentagEntitySearchRepository, times(0)).save(aufgabentagEntity);
    }

    @Test
    @Transactional
    public void deleteAufgabentagEntity() throws Exception {
        // Initialize the database
        aufgabentagEntityRepository.saveAndFlush(aufgabentagEntity);

        int databaseSizeBeforeDelete = aufgabentagEntityRepository.findAll().size();

        // Delete the aufgabentagEntity
        restAufgabentagEntityMockMvc.perform(delete("/api/aufgabentag-entities/{id}", aufgabentagEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AufgabentagEntity> aufgabentagEntityList = aufgabentagEntityRepository.findAll();
        assertThat(aufgabentagEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AufgabentagEntity in Elasticsearch
        verify(mockAufgabentagEntitySearchRepository, times(1)).deleteById(aufgabentagEntity.getId());
    }

    @Test
    @Transactional
    public void searchAufgabentagEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        aufgabentagEntityRepository.saveAndFlush(aufgabentagEntity);
        when(mockAufgabentagEntitySearchRepository.search(queryStringQuery("id:" + aufgabentagEntity.getId())))
            .thenReturn(Collections.singletonList(aufgabentagEntity));

        // Search the aufgabentagEntity
        restAufgabentagEntityMockMvc.perform(get("/api/_search/aufgabentag-entities?query=id:" + aufgabentagEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aufgabentagEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }
}
