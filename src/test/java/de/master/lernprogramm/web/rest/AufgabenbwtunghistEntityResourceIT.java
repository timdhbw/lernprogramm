package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.LernprogrammApp;
import de.master.lernprogramm.repository.entity.AufgabenbwtunghistEntity;
import de.master.lernprogramm.repository.AufgabenbwtunghistEntityRepository;
import de.master.lernprogramm.repository.search.AufgabenbwtunghistEntitySearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AufgabenbwtunghistEntityResource} REST controller.
 */
@SpringBootTest(classes = LernprogrammApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AufgabenbwtunghistEntityResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_BEWERTUNGSVERAENDERUNG = 1;
    private static final Integer UPDATED_BEWERTUNGSVERAENDERUNG = 2;

    @Autowired
    private AufgabenbwtunghistEntityRepository aufgabenbwtunghistEntityRepository;

    /**
     * This repository is mocked in the de.master.lernprogramm.repository.search test package.
     *
     * @see de.master.lernprogramm.repository.search.AufgabenbwtunghistEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private AufgabenbwtunghistEntitySearchRepository mockAufgabenbwtunghistEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAufgabenbwtunghistEntityMockMvc;

    private AufgabenbwtunghistEntity aufgabenbwtunghistEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AufgabenbwtunghistEntity createEntity(EntityManager em) {
        AufgabenbwtunghistEntity aufgabenbwtunghistEntity = new AufgabenbwtunghistEntity()
            .datum(DEFAULT_DATUM)
            .bewertungsveraenderung(DEFAULT_BEWERTUNGSVERAENDERUNG);
        return aufgabenbwtunghistEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AufgabenbwtunghistEntity createUpdatedEntity(EntityManager em) {
        AufgabenbwtunghistEntity aufgabenbwtunghistEntity = new AufgabenbwtunghistEntity()
            .datum(UPDATED_DATUM)
            .bewertungsveraenderung(UPDATED_BEWERTUNGSVERAENDERUNG);
        return aufgabenbwtunghistEntity;
    }

    @BeforeEach
    public void initTest() {
        aufgabenbwtunghistEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAufgabenbwtunghistEntity() throws Exception {
        int databaseSizeBeforeCreate = aufgabenbwtunghistEntityRepository.findAll().size();
        // Create the AufgabenbwtunghistEntity
        restAufgabenbwtunghistEntityMockMvc.perform(post("/api/aufgabenbwtunghist-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabenbwtunghistEntity)))
            .andExpect(status().isCreated());

        // Validate the AufgabenbwtunghistEntity in the database
        List<AufgabenbwtunghistEntity> aufgabenbwtunghistEntityList = aufgabenbwtunghistEntityRepository.findAll();
        assertThat(aufgabenbwtunghistEntityList).hasSize(databaseSizeBeforeCreate + 1);
        AufgabenbwtunghistEntity testAufgabenbwtunghistEntity = aufgabenbwtunghistEntityList.get(aufgabenbwtunghistEntityList.size() - 1);
        assertThat(testAufgabenbwtunghistEntity.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testAufgabenbwtunghistEntity.getBewertungsveraenderung()).isEqualTo(DEFAULT_BEWERTUNGSVERAENDERUNG);

        // Validate the AufgabenbwtunghistEntity in Elasticsearch
        verify(mockAufgabenbwtunghistEntitySearchRepository, times(1)).save(testAufgabenbwtunghistEntity);
    }

    @Test
    @Transactional
    public void createAufgabenbwtunghistEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aufgabenbwtunghistEntityRepository.findAll().size();

        // Create the AufgabenbwtunghistEntity with an existing ID
        aufgabenbwtunghistEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAufgabenbwtunghistEntityMockMvc.perform(post("/api/aufgabenbwtunghist-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabenbwtunghistEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AufgabenbwtunghistEntity in the database
        List<AufgabenbwtunghistEntity> aufgabenbwtunghistEntityList = aufgabenbwtunghistEntityRepository.findAll();
        assertThat(aufgabenbwtunghistEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the AufgabenbwtunghistEntity in Elasticsearch
        verify(mockAufgabenbwtunghistEntitySearchRepository, times(0)).save(aufgabenbwtunghistEntity);
    }


    @Test
    @Transactional
    public void getAllAufgabenbwtunghistEntities() throws Exception {
        // Initialize the database
        aufgabenbwtunghistEntityRepository.saveAndFlush(aufgabenbwtunghistEntity);

        // Get all the aufgabenbwtunghistEntityList
        restAufgabenbwtunghistEntityMockMvc.perform(get("/api/aufgabenbwtunghist-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aufgabenbwtunghistEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].bewertungsveraenderung").value(hasItem(DEFAULT_BEWERTUNGSVERAENDERUNG)));
    }

    @Test
    @Transactional
    public void getAufgabenbwtunghistEntity() throws Exception {
        // Initialize the database
        aufgabenbwtunghistEntityRepository.saveAndFlush(aufgabenbwtunghistEntity);

        // Get the aufgabenbwtunghistEntity
        restAufgabenbwtunghistEntityMockMvc.perform(get("/api/aufgabenbwtunghist-entities/{id}", aufgabenbwtunghistEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aufgabenbwtunghistEntity.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.bewertungsveraenderung").value(DEFAULT_BEWERTUNGSVERAENDERUNG));
    }
    @Test
    @Transactional
    public void getNonExistingAufgabenbwtunghistEntity() throws Exception {
        // Get the aufgabenbwtunghistEntity
        restAufgabenbwtunghistEntityMockMvc.perform(get("/api/aufgabenbwtunghist-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAufgabenbwtunghistEntity() throws Exception {
        // Initialize the database
        aufgabenbwtunghistEntityRepository.saveAndFlush(aufgabenbwtunghistEntity);

        int databaseSizeBeforeUpdate = aufgabenbwtunghistEntityRepository.findAll().size();

        // Update the aufgabenbwtunghistEntity
        AufgabenbwtunghistEntity updatedAufgabenbwtunghistEntity = aufgabenbwtunghistEntityRepository.findById(aufgabenbwtunghistEntity.getId()).get();
        // Disconnect from session so that the updates on updatedAufgabenbwtunghistEntity are not directly saved in db
        em.detach(updatedAufgabenbwtunghistEntity);
        updatedAufgabenbwtunghistEntity
            .datum(UPDATED_DATUM)
            .bewertungsveraenderung(UPDATED_BEWERTUNGSVERAENDERUNG);

        restAufgabenbwtunghistEntityMockMvc.perform(put("/api/aufgabenbwtunghist-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAufgabenbwtunghistEntity)))
            .andExpect(status().isOk());

        // Validate the AufgabenbwtunghistEntity in the database
        List<AufgabenbwtunghistEntity> aufgabenbwtunghistEntityList = aufgabenbwtunghistEntityRepository.findAll();
        assertThat(aufgabenbwtunghistEntityList).hasSize(databaseSizeBeforeUpdate);
        AufgabenbwtunghistEntity testAufgabenbwtunghistEntity = aufgabenbwtunghistEntityList.get(aufgabenbwtunghistEntityList.size() - 1);
        assertThat(testAufgabenbwtunghistEntity.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testAufgabenbwtunghistEntity.getBewertungsveraenderung()).isEqualTo(UPDATED_BEWERTUNGSVERAENDERUNG);

        // Validate the AufgabenbwtunghistEntity in Elasticsearch
        verify(mockAufgabenbwtunghistEntitySearchRepository, times(1)).save(testAufgabenbwtunghistEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingAufgabenbwtunghistEntity() throws Exception {
        int databaseSizeBeforeUpdate = aufgabenbwtunghistEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAufgabenbwtunghistEntityMockMvc.perform(put("/api/aufgabenbwtunghist-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabenbwtunghistEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AufgabenbwtunghistEntity in the database
        List<AufgabenbwtunghistEntity> aufgabenbwtunghistEntityList = aufgabenbwtunghistEntityRepository.findAll();
        assertThat(aufgabenbwtunghistEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AufgabenbwtunghistEntity in Elasticsearch
        verify(mockAufgabenbwtunghistEntitySearchRepository, times(0)).save(aufgabenbwtunghistEntity);
    }

    @Test
    @Transactional
    public void deleteAufgabenbwtunghistEntity() throws Exception {
        // Initialize the database
        aufgabenbwtunghistEntityRepository.saveAndFlush(aufgabenbwtunghistEntity);

        int databaseSizeBeforeDelete = aufgabenbwtunghistEntityRepository.findAll().size();

        // Delete the aufgabenbwtunghistEntity
        restAufgabenbwtunghistEntityMockMvc.perform(delete("/api/aufgabenbwtunghist-entities/{id}", aufgabenbwtunghistEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AufgabenbwtunghistEntity> aufgabenbwtunghistEntityList = aufgabenbwtunghistEntityRepository.findAll();
        assertThat(aufgabenbwtunghistEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AufgabenbwtunghistEntity in Elasticsearch
        verify(mockAufgabenbwtunghistEntitySearchRepository, times(1)).deleteById(aufgabenbwtunghistEntity.getId());
    }

    @Test
    @Transactional
    public void searchAufgabenbwtunghistEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        aufgabenbwtunghistEntityRepository.saveAndFlush(aufgabenbwtunghistEntity);
        when(mockAufgabenbwtunghistEntitySearchRepository.search(queryStringQuery("id:" + aufgabenbwtunghistEntity.getId())))
            .thenReturn(Collections.singletonList(aufgabenbwtunghistEntity));

        // Search the aufgabenbwtunghistEntity
        restAufgabenbwtunghistEntityMockMvc.perform(get("/api/_search/aufgabenbwtunghist-entities?query=id:" + aufgabenbwtunghistEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aufgabenbwtunghistEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].bewertungsveraenderung").value(hasItem(DEFAULT_BEWERTUNGSVERAENDERUNG)));
    }
}
