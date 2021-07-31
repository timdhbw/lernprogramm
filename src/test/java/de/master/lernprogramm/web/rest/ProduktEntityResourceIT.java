package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.LernprogrammApp;
import de.master.lernprogramm.domain.ProduktEntity;
import de.master.lernprogramm.repository.ProduktEntityRepository;
import de.master.lernprogramm.repository.search.ProduktEntitySearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
 * Integration tests for the {@link ProduktEntityResource} REST controller.
 */
@SpringBootTest(classes = LernprogrammApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProduktEntityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BEWERTUNG = 1;
    private static final Integer UPDATED_BEWERTUNG = 2;

    @Autowired
    private ProduktEntityRepository produktEntityRepository;

    /**
     * This repository is mocked in the de.master.lernprogramm.repository.search test package.
     *
     * @see de.master.lernprogramm.repository.search.ProduktEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private ProduktEntitySearchRepository mockProduktEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduktEntityMockMvc;

    private ProduktEntity produktEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProduktEntity createEntity(EntityManager em) {
        ProduktEntity produktEntity = new ProduktEntity()
            .name(DEFAULT_NAME)
            .bewertung(DEFAULT_BEWERTUNG);
        return produktEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProduktEntity createUpdatedEntity(EntityManager em) {
        ProduktEntity produktEntity = new ProduktEntity()
            .name(UPDATED_NAME)
            .bewertung(UPDATED_BEWERTUNG);
        return produktEntity;
    }

    @BeforeEach
    public void initTest() {
        produktEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduktEntity() throws Exception {
        int databaseSizeBeforeCreate = produktEntityRepository.findAll().size();
        // Create the ProduktEntity
        restProduktEntityMockMvc.perform(post("/api/produkt-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produktEntity)))
            .andExpect(status().isCreated());

        // Validate the ProduktEntity in the database
        List<ProduktEntity> produktEntityList = produktEntityRepository.findAll();
        assertThat(produktEntityList).hasSize(databaseSizeBeforeCreate + 1);
        ProduktEntity testProduktEntity = produktEntityList.get(produktEntityList.size() - 1);
        assertThat(testProduktEntity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduktEntity.getBewertung()).isEqualTo(DEFAULT_BEWERTUNG);

        // Validate the ProduktEntity in Elasticsearch
        verify(mockProduktEntitySearchRepository, times(1)).save(testProduktEntity);
    }

    @Test
    @Transactional
    public void createProduktEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produktEntityRepository.findAll().size();

        // Create the ProduktEntity with an existing ID
        produktEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduktEntityMockMvc.perform(post("/api/produkt-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produktEntity)))
            .andExpect(status().isBadRequest());

        // Validate the ProduktEntity in the database
        List<ProduktEntity> produktEntityList = produktEntityRepository.findAll();
        assertThat(produktEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProduktEntity in Elasticsearch
        verify(mockProduktEntitySearchRepository, times(0)).save(produktEntity);
    }


    @Test
    @Transactional
    public void getAllProduktEntities() throws Exception {
        // Initialize the database
        produktEntityRepository.saveAndFlush(produktEntity);

        // Get all the produktEntityList
        restProduktEntityMockMvc.perform(get("/api/produkt-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produktEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bewertung").value(hasItem(DEFAULT_BEWERTUNG)));
    }
    
    @Test
    @Transactional
    public void getProduktEntity() throws Exception {
        // Initialize the database
        produktEntityRepository.saveAndFlush(produktEntity);

        // Get the produktEntity
        restProduktEntityMockMvc.perform(get("/api/produkt-entities/{id}", produktEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produktEntity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bewertung").value(DEFAULT_BEWERTUNG));
    }
    @Test
    @Transactional
    public void getNonExistingProduktEntity() throws Exception {
        // Get the produktEntity
        restProduktEntityMockMvc.perform(get("/api/produkt-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduktEntity() throws Exception {
        // Initialize the database
        produktEntityRepository.saveAndFlush(produktEntity);

        int databaseSizeBeforeUpdate = produktEntityRepository.findAll().size();

        // Update the produktEntity
        ProduktEntity updatedProduktEntity = produktEntityRepository.findById(produktEntity.getId()).get();
        // Disconnect from session so that the updates on updatedProduktEntity are not directly saved in db
        em.detach(updatedProduktEntity);
        updatedProduktEntity
            .name(UPDATED_NAME)
            .bewertung(UPDATED_BEWERTUNG);

        restProduktEntityMockMvc.perform(put("/api/produkt-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduktEntity)))
            .andExpect(status().isOk());

        // Validate the ProduktEntity in the database
        List<ProduktEntity> produktEntityList = produktEntityRepository.findAll();
        assertThat(produktEntityList).hasSize(databaseSizeBeforeUpdate);
        ProduktEntity testProduktEntity = produktEntityList.get(produktEntityList.size() - 1);
        assertThat(testProduktEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduktEntity.getBewertung()).isEqualTo(UPDATED_BEWERTUNG);

        // Validate the ProduktEntity in Elasticsearch
        verify(mockProduktEntitySearchRepository, times(1)).save(testProduktEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingProduktEntity() throws Exception {
        int databaseSizeBeforeUpdate = produktEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduktEntityMockMvc.perform(put("/api/produkt-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produktEntity)))
            .andExpect(status().isBadRequest());

        // Validate the ProduktEntity in the database
        List<ProduktEntity> produktEntityList = produktEntityRepository.findAll();
        assertThat(produktEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProduktEntity in Elasticsearch
        verify(mockProduktEntitySearchRepository, times(0)).save(produktEntity);
    }

    @Test
    @Transactional
    public void deleteProduktEntity() throws Exception {
        // Initialize the database
        produktEntityRepository.saveAndFlush(produktEntity);

        int databaseSizeBeforeDelete = produktEntityRepository.findAll().size();

        // Delete the produktEntity
        restProduktEntityMockMvc.perform(delete("/api/produkt-entities/{id}", produktEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProduktEntity> produktEntityList = produktEntityRepository.findAll();
        assertThat(produktEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProduktEntity in Elasticsearch
        verify(mockProduktEntitySearchRepository, times(1)).deleteById(produktEntity.getId());
    }

    @Test
    @Transactional
    public void searchProduktEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        produktEntityRepository.saveAndFlush(produktEntity);
        when(mockProduktEntitySearchRepository.search(queryStringQuery("id:" + produktEntity.getId())))
            .thenReturn(Collections.singletonList(produktEntity));

        // Search the produktEntity
        restProduktEntityMockMvc.perform(get("/api/_search/produkt-entities?query=id:" + produktEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produktEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bewertung").value(hasItem(DEFAULT_BEWERTUNG)));
    }
}
