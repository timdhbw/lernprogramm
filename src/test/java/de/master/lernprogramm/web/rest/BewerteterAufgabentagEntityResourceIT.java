package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.LernprogrammApp;
import de.master.lernprogramm.repository.entity.BewerteterAufgabentagEntity;
import de.master.lernprogramm.repository.BewerteterAufgabentagEntityRepository;
import de.master.lernprogramm.repository.search.BewerteterAufgabentagEntitySearchRepository;

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
 * Integration tests for the {@link BewerteterAufgabentagEntityResource} REST controller.
 */
@SpringBootTest(classes = LernprogrammApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BewerteterAufgabentagEntityResourceIT {

    private static final Integer DEFAULT_BEWERTUNG = 1;
    private static final Integer UPDATED_BEWERTUNG = 2;

    @Autowired
    private BewerteterAufgabentagEntityRepository bewerteterAufgabentagEntityRepository;

    /**
     * This repository is mocked in the de.master.lernprogramm.repository.search test package.
     *
     * @see de.master.lernprogramm.repository.search.BewerteterAufgabentagEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private BewerteterAufgabentagEntitySearchRepository mockBewerteterAufgabentagEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBewerteterAufgabentagEntityMockMvc;

    private BewerteterAufgabentagEntity bewerteterAufgabentagEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BewerteterAufgabentagEntity createEntity(EntityManager em) {
        BewerteterAufgabentagEntity bewerteterAufgabentagEntity = new BewerteterAufgabentagEntity()
            .bewertung(DEFAULT_BEWERTUNG);
        return bewerteterAufgabentagEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BewerteterAufgabentagEntity createUpdatedEntity(EntityManager em) {
        BewerteterAufgabentagEntity bewerteterAufgabentagEntity = new BewerteterAufgabentagEntity()
            .bewertung(UPDATED_BEWERTUNG);
        return bewerteterAufgabentagEntity;
    }

    @BeforeEach
    public void initTest() {
        bewerteterAufgabentagEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createBewerteterAufgabentagEntity() throws Exception {
        int databaseSizeBeforeCreate = bewerteterAufgabentagEntityRepository.findAll().size();
        // Create the BewerteterAufgabentagEntity
        restBewerteterAufgabentagEntityMockMvc.perform(post("/api/bewerteter-aufgabentag-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bewerteterAufgabentagEntity)))
            .andExpect(status().isCreated());

        // Validate the BewerteterAufgabentagEntity in the database
        List<BewerteterAufgabentagEntity> bewerteterAufgabentagEntityList = bewerteterAufgabentagEntityRepository.findAll();
        assertThat(bewerteterAufgabentagEntityList).hasSize(databaseSizeBeforeCreate + 1);
        BewerteterAufgabentagEntity testBewerteterAufgabentagEntity = bewerteterAufgabentagEntityList.get(bewerteterAufgabentagEntityList.size() - 1);
        assertThat(testBewerteterAufgabentagEntity.getBewertung()).isEqualTo(DEFAULT_BEWERTUNG);

        // Validate the BewerteterAufgabentagEntity in Elasticsearch
        verify(mockBewerteterAufgabentagEntitySearchRepository, times(1)).save(testBewerteterAufgabentagEntity);
    }

    @Test
    @Transactional
    public void createBewerteterAufgabentagEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bewerteterAufgabentagEntityRepository.findAll().size();

        // Create the BewerteterAufgabentagEntity with an existing ID
        bewerteterAufgabentagEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBewerteterAufgabentagEntityMockMvc.perform(post("/api/bewerteter-aufgabentag-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bewerteterAufgabentagEntity)))
            .andExpect(status().isBadRequest());

        // Validate the BewerteterAufgabentagEntity in the database
        List<BewerteterAufgabentagEntity> bewerteterAufgabentagEntityList = bewerteterAufgabentagEntityRepository.findAll();
        assertThat(bewerteterAufgabentagEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the BewerteterAufgabentagEntity in Elasticsearch
        verify(mockBewerteterAufgabentagEntitySearchRepository, times(0)).save(bewerteterAufgabentagEntity);
    }


    @Test
    @Transactional
    public void getAllBewerteterAufgabentagEntities() throws Exception {
        // Initialize the database
        bewerteterAufgabentagEntityRepository.saveAndFlush(bewerteterAufgabentagEntity);

        // Get all the bewerteterAufgabentagEntityList
        restBewerteterAufgabentagEntityMockMvc.perform(get("/api/bewerteter-aufgabentag-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bewerteterAufgabentagEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].bewertung").value(hasItem(DEFAULT_BEWERTUNG)));
    }

    @Test
    @Transactional
    public void getBewerteterAufgabentagEntity() throws Exception {
        // Initialize the database
        bewerteterAufgabentagEntityRepository.saveAndFlush(bewerteterAufgabentagEntity);

        // Get the bewerteterAufgabentagEntity
        restBewerteterAufgabentagEntityMockMvc.perform(get("/api/bewerteter-aufgabentag-entities/{id}", bewerteterAufgabentagEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bewerteterAufgabentagEntity.getId().intValue()))
            .andExpect(jsonPath("$.bewertung").value(DEFAULT_BEWERTUNG));
    }
    @Test
    @Transactional
    public void getNonExistingBewerteterAufgabentagEntity() throws Exception {
        // Get the bewerteterAufgabentagEntity
        restBewerteterAufgabentagEntityMockMvc.perform(get("/api/bewerteter-aufgabentag-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBewerteterAufgabentagEntity() throws Exception {
        // Initialize the database
        bewerteterAufgabentagEntityRepository.saveAndFlush(bewerteterAufgabentagEntity);

        int databaseSizeBeforeUpdate = bewerteterAufgabentagEntityRepository.findAll().size();

        // Update the bewerteterAufgabentagEntity
        BewerteterAufgabentagEntity updatedBewerteterAufgabentagEntity = bewerteterAufgabentagEntityRepository.findById(bewerteterAufgabentagEntity.getId()).get();
        // Disconnect from session so that the updates on updatedBewerteterAufgabentagEntity are not directly saved in db
        em.detach(updatedBewerteterAufgabentagEntity);
        updatedBewerteterAufgabentagEntity
            .bewertung(UPDATED_BEWERTUNG);

        restBewerteterAufgabentagEntityMockMvc.perform(put("/api/bewerteter-aufgabentag-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBewerteterAufgabentagEntity)))
            .andExpect(status().isOk());

        // Validate the BewerteterAufgabentagEntity in the database
        List<BewerteterAufgabentagEntity> bewerteterAufgabentagEntityList = bewerteterAufgabentagEntityRepository.findAll();
        assertThat(bewerteterAufgabentagEntityList).hasSize(databaseSizeBeforeUpdate);
        BewerteterAufgabentagEntity testBewerteterAufgabentagEntity = bewerteterAufgabentagEntityList.get(bewerteterAufgabentagEntityList.size() - 1);
        assertThat(testBewerteterAufgabentagEntity.getBewertung()).isEqualTo(UPDATED_BEWERTUNG);

        // Validate the BewerteterAufgabentagEntity in Elasticsearch
        verify(mockBewerteterAufgabentagEntitySearchRepository, times(1)).save(testBewerteterAufgabentagEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingBewerteterAufgabentagEntity() throws Exception {
        int databaseSizeBeforeUpdate = bewerteterAufgabentagEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBewerteterAufgabentagEntityMockMvc.perform(put("/api/bewerteter-aufgabentag-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bewerteterAufgabentagEntity)))
            .andExpect(status().isBadRequest());

        // Validate the BewerteterAufgabentagEntity in the database
        List<BewerteterAufgabentagEntity> bewerteterAufgabentagEntityList = bewerteterAufgabentagEntityRepository.findAll();
        assertThat(bewerteterAufgabentagEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BewerteterAufgabentagEntity in Elasticsearch
        verify(mockBewerteterAufgabentagEntitySearchRepository, times(0)).save(bewerteterAufgabentagEntity);
    }

    @Test
    @Transactional
    public void deleteBewerteterAufgabentagEntity() throws Exception {
        // Initialize the database
        bewerteterAufgabentagEntityRepository.saveAndFlush(bewerteterAufgabentagEntity);

        int databaseSizeBeforeDelete = bewerteterAufgabentagEntityRepository.findAll().size();

        // Delete the bewerteterAufgabentagEntity
        restBewerteterAufgabentagEntityMockMvc.perform(delete("/api/bewerteter-aufgabentag-entities/{id}", bewerteterAufgabentagEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BewerteterAufgabentagEntity> bewerteterAufgabentagEntityList = bewerteterAufgabentagEntityRepository.findAll();
        assertThat(bewerteterAufgabentagEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BewerteterAufgabentagEntity in Elasticsearch
        verify(mockBewerteterAufgabentagEntitySearchRepository, times(1)).deleteById(bewerteterAufgabentagEntity.getId());
    }

    @Test
    @Transactional
    public void searchBewerteterAufgabentagEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        bewerteterAufgabentagEntityRepository.saveAndFlush(bewerteterAufgabentagEntity);
        when(mockBewerteterAufgabentagEntitySearchRepository.search(queryStringQuery("id:" + bewerteterAufgabentagEntity.getId())))
            .thenReturn(Collections.singletonList(bewerteterAufgabentagEntity));

        // Search the bewerteterAufgabentagEntity
        restBewerteterAufgabentagEntityMockMvc.perform(get("/api/_search/bewerteter-aufgabentag-entities?query=id:" + bewerteterAufgabentagEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bewerteterAufgabentagEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].bewertung").value(hasItem(DEFAULT_BEWERTUNG)));
    }
}
