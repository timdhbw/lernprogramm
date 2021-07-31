package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.LernprogrammApp;
import de.master.lernprogramm.repository.entity.AufgabeEntity;
import de.master.lernprogramm.repository.AufgabeEntityRepository;
import de.master.lernprogramm.repository.search.AufgabeEntitySearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.master.lernprogramm.domain.enumeration.KategorieEnum;
/**
 * Integration tests for the {@link AufgabeEntityResource} REST controller.
 */
@SpringBootTest(classes = LernprogrammApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AufgabeEntityResourceIT {

    private static final String DEFAULT_AUFGABENTITEL = "AAAAAAAAAA";
    private static final String UPDATED_AUFGABENTITEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_BEWERTUNG = 1;
    private static final Integer UPDATED_BEWERTUNG = 2;

    private static final KategorieEnum DEFAULT_KATEGORIE = KategorieEnum.SOFTWAREENTWICKLUNG;
    private static final KategorieEnum UPDATED_KATEGORIE = KategorieEnum.SOFTWAREENTWICKLUNG;

    @Autowired
    private AufgabeEntityRepository aufgabeEntityRepository;

    @Mock
    private AufgabeEntityRepository aufgabeEntityRepositoryMock;

    /**
     * This repository is mocked in the de.master.lernprogramm.repository.search test package.
     *
     * @see de.master.lernprogramm.repository.search.AufgabeEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private AufgabeEntitySearchRepository mockAufgabeEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAufgabeEntityMockMvc;

    private AufgabeEntity aufgabeEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AufgabeEntity createEntity(EntityManager em) {
        AufgabeEntity aufgabeEntity = new AufgabeEntity()
            .aufgabentitel(DEFAULT_AUFGABENTITEL)
            .bewertung(DEFAULT_BEWERTUNG)
            .kategorie(DEFAULT_KATEGORIE);
        return aufgabeEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AufgabeEntity createUpdatedEntity(EntityManager em) {
        AufgabeEntity aufgabeEntity = new AufgabeEntity()
            .aufgabentitel(UPDATED_AUFGABENTITEL)
            .bewertung(UPDATED_BEWERTUNG)
            .kategorie(UPDATED_KATEGORIE);
        return aufgabeEntity;
    }

    @BeforeEach
    public void initTest() {
        aufgabeEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAufgabeEntity() throws Exception {
        int databaseSizeBeforeCreate = aufgabeEntityRepository.findAll().size();
        // Create the AufgabeEntity
        restAufgabeEntityMockMvc.perform(post("/api/aufgabe-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabeEntity)))
            .andExpect(status().isCreated());

        // Validate the AufgabeEntity in the database
        List<AufgabeEntity> aufgabeEntityList = aufgabeEntityRepository.findAll();
        assertThat(aufgabeEntityList).hasSize(databaseSizeBeforeCreate + 1);
        AufgabeEntity testAufgabeEntity = aufgabeEntityList.get(aufgabeEntityList.size() - 1);
        assertThat(testAufgabeEntity.getAufgabentitel()).isEqualTo(DEFAULT_AUFGABENTITEL);
        assertThat(testAufgabeEntity.getBewertung()).isEqualTo(DEFAULT_BEWERTUNG);
        assertThat(testAufgabeEntity.getKategorie()).isEqualTo(DEFAULT_KATEGORIE);

        // Validate the AufgabeEntity in Elasticsearch
        verify(mockAufgabeEntitySearchRepository, times(1)).save(testAufgabeEntity);
    }

    @Test
    @Transactional
    public void createAufgabeEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aufgabeEntityRepository.findAll().size();

        // Create the AufgabeEntity with an existing ID
        aufgabeEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAufgabeEntityMockMvc.perform(post("/api/aufgabe-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabeEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AufgabeEntity in the database
        List<AufgabeEntity> aufgabeEntityList = aufgabeEntityRepository.findAll();
        assertThat(aufgabeEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the AufgabeEntity in Elasticsearch
        verify(mockAufgabeEntitySearchRepository, times(0)).save(aufgabeEntity);
    }


    @Test
    @Transactional
    public void checkKategorieIsRequired() throws Exception {
        int databaseSizeBeforeTest = aufgabeEntityRepository.findAll().size();
        // set the field null
        aufgabeEntity.setKategorie(null);

        // Create the AufgabeEntity, which fails.


        restAufgabeEntityMockMvc.perform(post("/api/aufgabe-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabeEntity)))
            .andExpect(status().isBadRequest());

        List<AufgabeEntity> aufgabeEntityList = aufgabeEntityRepository.findAll();
        assertThat(aufgabeEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAufgabeEntities() throws Exception {
        // Initialize the database
        aufgabeEntityRepository.saveAndFlush(aufgabeEntity);

        // Get all the aufgabeEntityList
        restAufgabeEntityMockMvc.perform(get("/api/aufgabe-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aufgabeEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].aufgabentitel").value(hasItem(DEFAULT_AUFGABENTITEL)))
            .andExpect(jsonPath("$.[*].bewertung").value(hasItem(DEFAULT_BEWERTUNG)))
            .andExpect(jsonPath("$.[*].kategorie").value(hasItem(DEFAULT_KATEGORIE.toString())));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAufgabeEntitiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(aufgabeEntityRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAufgabeEntityMockMvc.perform(get("/api/aufgabe-entities?eagerload=true"))
            .andExpect(status().isOk());

        verify(aufgabeEntityRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAufgabeEntitiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(aufgabeEntityRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAufgabeEntityMockMvc.perform(get("/api/aufgabe-entities?eagerload=true"))
            .andExpect(status().isOk());

        verify(aufgabeEntityRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAufgabeEntity() throws Exception {
        // Initialize the database
        aufgabeEntityRepository.saveAndFlush(aufgabeEntity);

        // Get the aufgabeEntity
        restAufgabeEntityMockMvc.perform(get("/api/aufgabe-entities/{id}", aufgabeEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aufgabeEntity.getId().intValue()))
            .andExpect(jsonPath("$.aufgabentitel").value(DEFAULT_AUFGABENTITEL))
            .andExpect(jsonPath("$.bewertung").value(DEFAULT_BEWERTUNG))
            .andExpect(jsonPath("$.kategorie").value(DEFAULT_KATEGORIE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAufgabeEntity() throws Exception {
        // Get the aufgabeEntity
        restAufgabeEntityMockMvc.perform(get("/api/aufgabe-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAufgabeEntity() throws Exception {
        // Initialize the database
        aufgabeEntityRepository.saveAndFlush(aufgabeEntity);

        int databaseSizeBeforeUpdate = aufgabeEntityRepository.findAll().size();

        // Update the aufgabeEntity
        AufgabeEntity updatedAufgabeEntity = aufgabeEntityRepository.findById(aufgabeEntity.getId()).get();
        // Disconnect from session so that the updates on updatedAufgabeEntity are not directly saved in db
        em.detach(updatedAufgabeEntity);
        updatedAufgabeEntity
            .aufgabentitel(UPDATED_AUFGABENTITEL)
            .bewertung(UPDATED_BEWERTUNG)
            .kategorie(UPDATED_KATEGORIE);

        restAufgabeEntityMockMvc.perform(put("/api/aufgabe-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAufgabeEntity)))
            .andExpect(status().isOk());

        // Validate the AufgabeEntity in the database
        List<AufgabeEntity> aufgabeEntityList = aufgabeEntityRepository.findAll();
        assertThat(aufgabeEntityList).hasSize(databaseSizeBeforeUpdate);
        AufgabeEntity testAufgabeEntity = aufgabeEntityList.get(aufgabeEntityList.size() - 1);
        assertThat(testAufgabeEntity.getAufgabentitel()).isEqualTo(UPDATED_AUFGABENTITEL);
        assertThat(testAufgabeEntity.getBewertung()).isEqualTo(UPDATED_BEWERTUNG);
        assertThat(testAufgabeEntity.getKategorie()).isEqualTo(UPDATED_KATEGORIE);

        // Validate the AufgabeEntity in Elasticsearch
        verify(mockAufgabeEntitySearchRepository, times(1)).save(testAufgabeEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingAufgabeEntity() throws Exception {
        int databaseSizeBeforeUpdate = aufgabeEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAufgabeEntityMockMvc.perform(put("/api/aufgabe-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aufgabeEntity)))
            .andExpect(status().isBadRequest());

        // Validate the AufgabeEntity in the database
        List<AufgabeEntity> aufgabeEntityList = aufgabeEntityRepository.findAll();
        assertThat(aufgabeEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AufgabeEntity in Elasticsearch
        verify(mockAufgabeEntitySearchRepository, times(0)).save(aufgabeEntity);
    }

    @Test
    @Transactional
    public void deleteAufgabeEntity() throws Exception {
        // Initialize the database
        aufgabeEntityRepository.saveAndFlush(aufgabeEntity);

        int databaseSizeBeforeDelete = aufgabeEntityRepository.findAll().size();

        // Delete the aufgabeEntity
        restAufgabeEntityMockMvc.perform(delete("/api/aufgabe-entities/{id}", aufgabeEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AufgabeEntity> aufgabeEntityList = aufgabeEntityRepository.findAll();
        assertThat(aufgabeEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AufgabeEntity in Elasticsearch
        verify(mockAufgabeEntitySearchRepository, times(1)).deleteById(aufgabeEntity.getId());
    }

    @Test
    @Transactional
    public void searchAufgabeEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        aufgabeEntityRepository.saveAndFlush(aufgabeEntity);
        when(mockAufgabeEntitySearchRepository.search(queryStringQuery("id:" + aufgabeEntity.getId())))
            .thenReturn(Collections.singletonList(aufgabeEntity));

        // Search the aufgabeEntity
        restAufgabeEntityMockMvc.perform(get("/api/_search/aufgabe-entities?query=id:" + aufgabeEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aufgabeEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].aufgabentitel").value(hasItem(DEFAULT_AUFGABENTITEL)))
            .andExpect(jsonPath("$.[*].bewertung").value(hasItem(DEFAULT_BEWERTUNG)))
            .andExpect(jsonPath("$.[*].kategorie").value(hasItem(DEFAULT_KATEGORIE.toString())));
    }
}
