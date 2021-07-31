package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.LernprogrammApp;
import de.master.lernprogramm.repository.entity.AufgabenteilEntity;
import de.master.lernprogramm.repository.AufgabenteilEntityRepository;
import de.master.lernprogramm.repository.search.AufgabenteilEntitySearchRepository;

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

import de.master.lernprogramm.domain.enumeration.AufgabenteiltypEnum;
/**
 * Integration tests for the {@link AufgabenteilEntityResource} REST controller.
 */
@SpringBootTest(classes = LernprogrammApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AufgabenteilEntityResourceIT {

    private static final Integer DEFAULT_LAUFEN_NR = 1;
    private static final Integer UPDATED_LAUFEN_NR = 2;

    private static final AufgabenteiltypEnum DEFAULT_AUFGABENTEILTYP = AufgabenteiltypEnum.TEXT;
    private static final AufgabenteiltypEnum UPDATED_AUFGABENTEILTYP = AufgabenteiltypEnum.TEXT;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private AufgabenteilEntityRepository aufgabenteilEntityRepository;

    /**
     * This repository is mocked in the de.master.lernprogramm.repository.search test package.
     *
     * @see de.master.lernprogramm.repository.search.AufgabenteilEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private AufgabenteilEntitySearchRepository mockAufgabenteilEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAufgabenteilEntityMockMvc;

    private AufgabenteilEntity aufgabenteilEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AufgabenteilEntity createEntity(EntityManager em) {
        AufgabenteilEntity aufgabenteilEntity = new AufgabenteilEntity()
            .laufenNr(DEFAULT_LAUFEN_NR)
            .aufgabenteiltyp(DEFAULT_AUFGABENTEILTYP)
            .text(DEFAULT_TEXT);
        return aufgabenteilEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AufgabenteilEntity createUpdatedEntity(EntityManager em) {
        AufgabenteilEntity aufgabenteilEntity = new AufgabenteilEntity()
            .laufenNr(UPDATED_LAUFEN_NR)
            .aufgabenteiltyp(UPDATED_AUFGABENTEILTYP)
            .text(UPDATED_TEXT);
        return aufgabenteilEntity;
    }

    @BeforeEach
    public void initTest() {
        aufgabenteilEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAufgabenteilEntity() throws Exception {
        int databaseSizeBeforeCreate = aufgabenteilEntityRepository.findAll().size();
        // Create the AufgabenteilEntity
        restAufgabenteilEntityMockMvc.perform(post("/api/aufgabenteil-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabenteilEntity)))
            .andExpect(status().isCreated());

        // Validate the AufgabenteilEntity in the database
        List<AufgabenteilEntity> aufgabenteilEntityList = aufgabenteilEntityRepository.findAll();
        assertThat(aufgabenteilEntityList).hasSize(databaseSizeBeforeCreate + 1);
        AufgabenteilEntity testAufgabenteilEntity = aufgabenteilEntityList.get(aufgabenteilEntityList.size() - 1);
        assertThat(testAufgabenteilEntity.getLaufenNr()).isEqualTo(DEFAULT_LAUFEN_NR);
        assertThat(testAufgabenteilEntity.getAufgabenteiltyp()).isEqualTo(DEFAULT_AUFGABENTEILTYP);
        assertThat(testAufgabenteilEntity.getText()).isEqualTo(DEFAULT_TEXT);

        // Validate the AufgabenteilEntity in Elasticsearch
        verify(mockAufgabenteilEntitySearchRepository, times(1)).save(testAufgabenteilEntity);
    }

    @Test
    @Transactional
    public void createAufgabenteilEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aufgabenteilEntityRepository.findAll().size();

        // Create the AufgabenteilEntity with an existing ID
        aufgabenteilEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAufgabenteilEntityMockMvc.perform(post("/api/aufgabenteil-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabenteilEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AufgabenteilEntity in the database
        List<AufgabenteilEntity> aufgabenteilEntityList = aufgabenteilEntityRepository.findAll();
        assertThat(aufgabenteilEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the AufgabenteilEntity in Elasticsearch
        verify(mockAufgabenteilEntitySearchRepository, times(0)).save(aufgabenteilEntity);
    }


    @Test
    @Transactional
    public void checkAufgabenteiltypIsRequired() throws Exception {
        int databaseSizeBeforeTest = aufgabenteilEntityRepository.findAll().size();
        // set the field null
        aufgabenteilEntity.setAufgabenteiltyp(null);

        // Create the AufgabenteilEntity, which fails.


        restAufgabenteilEntityMockMvc.perform(post("/api/aufgabenteil-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabenteilEntity)))
            .andExpect(status().isBadRequest());

        List<AufgabenteilEntity> aufgabenteilEntityList = aufgabenteilEntityRepository.findAll();
        assertThat(aufgabenteilEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAufgabenteilEntities() throws Exception {
        // Initialize the database
        aufgabenteilEntityRepository.saveAndFlush(aufgabenteilEntity);

        // Get all the aufgabenteilEntityList
        restAufgabenteilEntityMockMvc.perform(get("/api/aufgabenteil-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aufgabenteilEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].laufenNr").value(hasItem(DEFAULT_LAUFEN_NR)))
            .andExpect(jsonPath("$.[*].aufgabenteiltyp").value(hasItem(DEFAULT_AUFGABENTEILTYP.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }

    @Test
    @Transactional
    public void getAufgabenteilEntity() throws Exception {
        // Initialize the database
        aufgabenteilEntityRepository.saveAndFlush(aufgabenteilEntity);

        // Get the aufgabenteilEntity
        restAufgabenteilEntityMockMvc.perform(get("/api/aufgabenteil-entities/{id}", aufgabenteilEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aufgabenteilEntity.getId().intValue()))
            .andExpect(jsonPath("$.laufenNr").value(DEFAULT_LAUFEN_NR))
            .andExpect(jsonPath("$.aufgabenteiltyp").value(DEFAULT_AUFGABENTEILTYP.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }
    @Test
    @Transactional
    public void getNonExistingAufgabenteilEntity() throws Exception {
        // Get the aufgabenteilEntity
        restAufgabenteilEntityMockMvc.perform(get("/api/aufgabenteil-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAufgabenteilEntity() throws Exception {
        // Initialize the database
        aufgabenteilEntityRepository.saveAndFlush(aufgabenteilEntity);

        int databaseSizeBeforeUpdate = aufgabenteilEntityRepository.findAll().size();

        // Update the aufgabenteilEntity
        AufgabenteilEntity updatedAufgabenteilEntity = aufgabenteilEntityRepository.findById(aufgabenteilEntity.getId()).get();
        // Disconnect from session so that the updates on updatedAufgabenteilEntity are not directly saved in db
        em.detach(updatedAufgabenteilEntity);
        updatedAufgabenteilEntity
            .laufenNr(UPDATED_LAUFEN_NR)
            .aufgabenteiltyp(UPDATED_AUFGABENTEILTYP)
            .text(UPDATED_TEXT);

        restAufgabenteilEntityMockMvc.perform(put("/api/aufgabenteil-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAufgabenteilEntity)))
            .andExpect(status().isOk());

        // Validate the AufgabenteilEntity in the database
        List<AufgabenteilEntity> aufgabenteilEntityList = aufgabenteilEntityRepository.findAll();
        assertThat(aufgabenteilEntityList).hasSize(databaseSizeBeforeUpdate);
        AufgabenteilEntity testAufgabenteilEntity = aufgabenteilEntityList.get(aufgabenteilEntityList.size() - 1);
        assertThat(testAufgabenteilEntity.getLaufenNr()).isEqualTo(UPDATED_LAUFEN_NR);
        assertThat(testAufgabenteilEntity.getAufgabenteiltyp()).isEqualTo(UPDATED_AUFGABENTEILTYP);
        assertThat(testAufgabenteilEntity.getText()).isEqualTo(UPDATED_TEXT);

        // Validate the AufgabenteilEntity in Elasticsearch
        verify(mockAufgabenteilEntitySearchRepository, times(1)).save(testAufgabenteilEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingAufgabenteilEntity() throws Exception {
        int databaseSizeBeforeUpdate = aufgabenteilEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAufgabenteilEntityMockMvc.perform(put("/api/aufgabenteil-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabenteilEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AufgabenteilEntity in the database
        List<AufgabenteilEntity> aufgabenteilEntityList = aufgabenteilEntityRepository.findAll();
        assertThat(aufgabenteilEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AufgabenteilEntity in Elasticsearch
        verify(mockAufgabenteilEntitySearchRepository, times(0)).save(aufgabenteilEntity);
    }

    @Test
    @Transactional
    public void deleteAufgabenteilEntity() throws Exception {
        // Initialize the database
        aufgabenteilEntityRepository.saveAndFlush(aufgabenteilEntity);

        int databaseSizeBeforeDelete = aufgabenteilEntityRepository.findAll().size();

        // Delete the aufgabenteilEntity
        restAufgabenteilEntityMockMvc.perform(delete("/api/aufgabenteil-entities/{id}", aufgabenteilEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AufgabenteilEntity> aufgabenteilEntityList = aufgabenteilEntityRepository.findAll();
        assertThat(aufgabenteilEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AufgabenteilEntity in Elasticsearch
        verify(mockAufgabenteilEntitySearchRepository, times(1)).deleteById(aufgabenteilEntity.getId());
    }

    @Test
    @Transactional
    public void searchAufgabenteilEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        aufgabenteilEntityRepository.saveAndFlush(aufgabenteilEntity);
        when(mockAufgabenteilEntitySearchRepository.search(queryStringQuery("id:" + aufgabenteilEntity.getId())))
            .thenReturn(Collections.singletonList(aufgabenteilEntity));

        // Search the aufgabenteilEntity
        restAufgabenteilEntityMockMvc.perform(get("/api/_search/aufgabenteil-entities?query=id:" + aufgabenteilEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aufgabenteilEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].laufenNr").value(hasItem(DEFAULT_LAUFEN_NR)))
            .andExpect(jsonPath("$.[*].aufgabenteiltyp").value(hasItem(DEFAULT_AUFGABENTEILTYP.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }
}
