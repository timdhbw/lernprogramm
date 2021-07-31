package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.LernprogrammApp;
import de.master.lernprogramm.repository.entity.AufgabenhistorieEntity;
import de.master.lernprogramm.repository.AufgabenhistorieEntityRepository;
import de.master.lernprogramm.repository.search.AufgabenhistorieEntitySearchRepository;

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
 * Integration tests for the {@link AufgabenhistorieEntityResource} REST controller.
 */
@SpringBootTest(classes = LernprogrammApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AufgabenhistorieEntityResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_BEWERTUNGSVERAENDERUNG = 1;
    private static final Integer UPDATED_BEWERTUNGSVERAENDERUNG = 2;

    @Autowired
    private AufgabenhistorieEntityRepository aufgabenhistorieEntityRepository;

    /**
     * This repository is mocked in the de.master.lernprogramm.repository.search test package.
     *
     * @see de.master.lernprogramm.repository.search.AufgabenhistorieEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private AufgabenhistorieEntitySearchRepository mockAufgabenhistorieEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAufgabenhistorieEntityMockMvc;

    private AufgabenhistorieEntity aufgabenhistorieEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AufgabenhistorieEntity createEntity(EntityManager em) {
        AufgabenhistorieEntity aufgabenhistorieEntity = new AufgabenhistorieEntity()
            .datum(DEFAULT_DATUM)
            .bewertungsveraenderung(DEFAULT_BEWERTUNGSVERAENDERUNG);
        return aufgabenhistorieEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AufgabenhistorieEntity createUpdatedEntity(EntityManager em) {
        AufgabenhistorieEntity aufgabenhistorieEntity = new AufgabenhistorieEntity()
            .datum(UPDATED_DATUM)
            .bewertungsveraenderung(UPDATED_BEWERTUNGSVERAENDERUNG);
        return aufgabenhistorieEntity;
    }

    @BeforeEach
    public void initTest() {
        aufgabenhistorieEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAufgabenhistorieEntity() throws Exception {
        int databaseSizeBeforeCreate = aufgabenhistorieEntityRepository.findAll().size();
        // Create the AufgabenhistorieEntity
        restAufgabenhistorieEntityMockMvc.perform(post("/api/aufgabenhistorie-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabenhistorieEntity)))
            .andExpect(status().isCreated());

        // Validate the AufgabenhistorieEntity in the database
        List<AufgabenhistorieEntity> aufgabenhistorieEntityList = aufgabenhistorieEntityRepository.findAll();
        assertThat(aufgabenhistorieEntityList).hasSize(databaseSizeBeforeCreate + 1);
        AufgabenhistorieEntity testAufgabenhistorieEntity = aufgabenhistorieEntityList.get(aufgabenhistorieEntityList.size() - 1);
        assertThat(testAufgabenhistorieEntity.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testAufgabenhistorieEntity.getBewertungsveraenderung()).isEqualTo(DEFAULT_BEWERTUNGSVERAENDERUNG);

        // Validate the AufgabenhistorieEntity in Elasticsearch
        verify(mockAufgabenhistorieEntitySearchRepository, times(1)).save(testAufgabenhistorieEntity);
    }

    @Test
    @Transactional
    public void createAufgabenhistorieEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aufgabenhistorieEntityRepository.findAll().size();

        // Create the AufgabenhistorieEntity with an existing ID
        aufgabenhistorieEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAufgabenhistorieEntityMockMvc.perform(post("/api/aufgabenhistorie-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabenhistorieEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AufgabenhistorieEntity in the database
        List<AufgabenhistorieEntity> aufgabenhistorieEntityList = aufgabenhistorieEntityRepository.findAll();
        assertThat(aufgabenhistorieEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the AufgabenhistorieEntity in Elasticsearch
        verify(mockAufgabenhistorieEntitySearchRepository, times(0)).save(aufgabenhistorieEntity);
    }


    @Test
    @Transactional
    public void getAllAufgabenhistorieEntities() throws Exception {
        // Initialize the database
        aufgabenhistorieEntityRepository.saveAndFlush(aufgabenhistorieEntity);

        // Get all the aufgabenhistorieEntityList
        restAufgabenhistorieEntityMockMvc.perform(get("/api/aufgabenhistorie-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aufgabenhistorieEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].bewertungsveraenderung").value(hasItem(DEFAULT_BEWERTUNGSVERAENDERUNG)));
    }

    @Test
    @Transactional
    public void getAufgabenhistorieEntity() throws Exception {
        // Initialize the database
        aufgabenhistorieEntityRepository.saveAndFlush(aufgabenhistorieEntity);

        // Get the aufgabenhistorieEntity
        restAufgabenhistorieEntityMockMvc.perform(get("/api/aufgabenhistorie-entities/{id}", aufgabenhistorieEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aufgabenhistorieEntity.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.bewertungsveraenderung").value(DEFAULT_BEWERTUNGSVERAENDERUNG));
    }
    @Test
    @Transactional
    public void getNonExistingAufgabenhistorieEntity() throws Exception {
        // Get the aufgabenhistorieEntity
        restAufgabenhistorieEntityMockMvc.perform(get("/api/aufgabenhistorie-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAufgabenhistorieEntity() throws Exception {
        // Initialize the database
        aufgabenhistorieEntityRepository.saveAndFlush(aufgabenhistorieEntity);

        int databaseSizeBeforeUpdate = aufgabenhistorieEntityRepository.findAll().size();

        // Update the aufgabenhistorieEntity
        AufgabenhistorieEntity updatedAufgabenhistorieEntity = aufgabenhistorieEntityRepository.findById(aufgabenhistorieEntity.getId()).get();
        // Disconnect from session so that the updates on updatedAufgabenhistorieEntity are not directly saved in db
        em.detach(updatedAufgabenhistorieEntity);
        updatedAufgabenhistorieEntity
            .datum(UPDATED_DATUM)
            .bewertungsveraenderung(UPDATED_BEWERTUNGSVERAENDERUNG);

        restAufgabenhistorieEntityMockMvc.perform(put("/api/aufgabenhistorie-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAufgabenhistorieEntity)))
            .andExpect(status().isOk());

        // Validate the AufgabenhistorieEntity in the database
        List<AufgabenhistorieEntity> aufgabenhistorieEntityList = aufgabenhistorieEntityRepository.findAll();
        assertThat(aufgabenhistorieEntityList).hasSize(databaseSizeBeforeUpdate);
        AufgabenhistorieEntity testAufgabenhistorieEntity = aufgabenhistorieEntityList.get(aufgabenhistorieEntityList.size() - 1);
        assertThat(testAufgabenhistorieEntity.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testAufgabenhistorieEntity.getBewertungsveraenderung()).isEqualTo(UPDATED_BEWERTUNGSVERAENDERUNG);

        // Validate the AufgabenhistorieEntity in Elasticsearch
        verify(mockAufgabenhistorieEntitySearchRepository, times(1)).save(testAufgabenhistorieEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingAufgabenhistorieEntity() throws Exception {
        int databaseSizeBeforeUpdate = aufgabenhistorieEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAufgabenhistorieEntityMockMvc.perform(put("/api/aufgabenhistorie-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabenhistorieEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AufgabenhistorieEntity in the database
        List<AufgabenhistorieEntity> aufgabenhistorieEntityList = aufgabenhistorieEntityRepository.findAll();
        assertThat(aufgabenhistorieEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AufgabenhistorieEntity in Elasticsearch
        verify(mockAufgabenhistorieEntitySearchRepository, times(0)).save(aufgabenhistorieEntity);
    }

    @Test
    @Transactional
    public void deleteAufgabenhistorieEntity() throws Exception {
        // Initialize the database
        aufgabenhistorieEntityRepository.saveAndFlush(aufgabenhistorieEntity);

        int databaseSizeBeforeDelete = aufgabenhistorieEntityRepository.findAll().size();

        // Delete the aufgabenhistorieEntity
        restAufgabenhistorieEntityMockMvc.perform(delete("/api/aufgabenhistorie-entities/{id}", aufgabenhistorieEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AufgabenhistorieEntity> aufgabenhistorieEntityList = aufgabenhistorieEntityRepository.findAll();
        assertThat(aufgabenhistorieEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AufgabenhistorieEntity in Elasticsearch
        verify(mockAufgabenhistorieEntitySearchRepository, times(1)).deleteById(aufgabenhistorieEntity.getId());
    }

    @Test
    @Transactional
    public void searchAufgabenhistorieEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        aufgabenhistorieEntityRepository.saveAndFlush(aufgabenhistorieEntity);
        when(mockAufgabenhistorieEntitySearchRepository.search(queryStringQuery("id:" + aufgabenhistorieEntity.getId())))
            .thenReturn(Collections.singletonList(aufgabenhistorieEntity));

        // Search the aufgabenhistorieEntity
        restAufgabenhistorieEntityMockMvc.perform(get("/api/_search/aufgabenhistorie-entities?query=id:" + aufgabenhistorieEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aufgabenhistorieEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].bewertungsveraenderung").value(hasItem(DEFAULT_BEWERTUNGSVERAENDERUNG)));
    }
}
