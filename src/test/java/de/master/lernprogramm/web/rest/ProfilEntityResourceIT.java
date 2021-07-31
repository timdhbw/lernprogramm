package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.LernprogrammApp;
import de.master.lernprogramm.repository.entity.ProfilEntity;
import de.master.lernprogramm.repository.ProfilEntityRepository;
import de.master.lernprogramm.repository.search.ProfilEntitySearchRepository;

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
 * Integration tests for the {@link ProfilEntityResource} REST controller.
 */
@SpringBootTest(classes = LernprogrammApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProfilEntityResourceIT {

    private static final String DEFAULT_PROFIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROFIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VORNAME = "AAAAAAAAAA";
    private static final String UPDATED_VORNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NACHNAME = "AAAAAAAAAA";
    private static final String UPDATED_NACHNAME = "BBBBBBBBBB";

    @Autowired
    private ProfilEntityRepository profilEntityRepository;

    /**
     * This repository is mocked in the de.master.lernprogramm.repository.search test package.
     *
     * @see de.master.lernprogramm.repository.search.ProfilEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private ProfilEntitySearchRepository mockProfilEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfilEntityMockMvc;

    private ProfilEntity profilEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfilEntity createEntity(EntityManager em) {
        ProfilEntity profilEntity = new ProfilEntity()
            .profilId(DEFAULT_PROFIL_ID)
            .vorname(DEFAULT_VORNAME)
            .nachname(DEFAULT_NACHNAME);
        return profilEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfilEntity createUpdatedEntity(EntityManager em) {
        ProfilEntity profilEntity = new ProfilEntity()
            .profilId(UPDATED_PROFIL_ID)
            .vorname(UPDATED_VORNAME)
            .nachname(UPDATED_NACHNAME);
        return profilEntity;
    }

    @BeforeEach
    public void initTest() {
        profilEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfilEntity() throws Exception {
        int databaseSizeBeforeCreate = profilEntityRepository.findAll().size();
        // Create the ProfilEntity
        restProfilEntityMockMvc.perform(post("/api/profil-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profilEntity)))
            .andExpect(status().isCreated());

        // Validate the ProfilEntity in the database
        List<ProfilEntity> profilEntityList = profilEntityRepository.findAll();
        assertThat(profilEntityList).hasSize(databaseSizeBeforeCreate + 1);
        ProfilEntity testProfilEntity = profilEntityList.get(profilEntityList.size() - 1);
        assertThat(testProfilEntity.getProfilId()).isEqualTo(DEFAULT_PROFIL_ID);
        assertThat(testProfilEntity.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testProfilEntity.getNachname()).isEqualTo(DEFAULT_NACHNAME);

        // Validate the ProfilEntity in Elasticsearch
        verify(mockProfilEntitySearchRepository, times(1)).save(testProfilEntity);
    }

    @Test
    @Transactional
    public void createProfilEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profilEntityRepository.findAll().size();

        // Create the ProfilEntity with an existing ID
        profilEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfilEntityMockMvc.perform(post("/api/profil-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profilEntity)))
            .andExpect(status().isBadRequest());

        // Validate the ProfilEntity in the database
        List<ProfilEntity> profilEntityList = profilEntityRepository.findAll();
        assertThat(profilEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProfilEntity in Elasticsearch
        verify(mockProfilEntitySearchRepository, times(0)).save(profilEntity);
    }


    @Test
    @Transactional
    public void checkProfilIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = profilEntityRepository.findAll().size();
        // set the field null
        profilEntity.setProfilId(null);

        // Create the ProfilEntity, which fails.


        restProfilEntityMockMvc.perform(post("/api/profil-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profilEntity)))
            .andExpect(status().isBadRequest());

        List<ProfilEntity> profilEntityList = profilEntityRepository.findAll();
        assertThat(profilEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfilEntities() throws Exception {
        // Initialize the database
        profilEntityRepository.saveAndFlush(profilEntity);

        // Get all the profilEntityList
        restProfilEntityMockMvc.perform(get("/api/profil-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profilEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].profilId").value(hasItem(DEFAULT_PROFIL_ID)))
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME)))
            .andExpect(jsonPath("$.[*].nachname").value(hasItem(DEFAULT_NACHNAME)));
    }

    @Test
    @Transactional
    public void getProfilEntity() throws Exception {
        // Initialize the database
        profilEntityRepository.saveAndFlush(profilEntity);

        // Get the profilEntity
        restProfilEntityMockMvc.perform(get("/api/profil-entities/{id}", profilEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profilEntity.getId().intValue()))
            .andExpect(jsonPath("$.profilId").value(DEFAULT_PROFIL_ID))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME))
            .andExpect(jsonPath("$.nachname").value(DEFAULT_NACHNAME));
    }
    @Test
    @Transactional
    public void getNonExistingProfilEntity() throws Exception {
        // Get the profilEntity
        restProfilEntityMockMvc.perform(get("/api/profil-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfilEntity() throws Exception {
        // Initialize the database
        profilEntityRepository.saveAndFlush(profilEntity);

        int databaseSizeBeforeUpdate = profilEntityRepository.findAll().size();

        // Update the profilEntity
        ProfilEntity updatedProfilEntity = profilEntityRepository.findById(profilEntity.getId()).get();
        // Disconnect from session so that the updates on updatedProfilEntity are not directly saved in db
        em.detach(updatedProfilEntity);
        updatedProfilEntity
            .profilId(UPDATED_PROFIL_ID)
            .vorname(UPDATED_VORNAME)
            .nachname(UPDATED_NACHNAME);

        restProfilEntityMockMvc.perform(put("/api/profil-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfilEntity)))
            .andExpect(status().isOk());

        // Validate the ProfilEntity in the database
        List<ProfilEntity> profilEntityList = profilEntityRepository.findAll();
        assertThat(profilEntityList).hasSize(databaseSizeBeforeUpdate);
        ProfilEntity testProfilEntity = profilEntityList.get(profilEntityList.size() - 1);
        assertThat(testProfilEntity.getProfilId()).isEqualTo(UPDATED_PROFIL_ID);
        assertThat(testProfilEntity.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testProfilEntity.getNachname()).isEqualTo(UPDATED_NACHNAME);

        // Validate the ProfilEntity in Elasticsearch
        verify(mockProfilEntitySearchRepository, times(1)).save(testProfilEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingProfilEntity() throws Exception {
        int databaseSizeBeforeUpdate = profilEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfilEntityMockMvc.perform(put("/api/profil-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profilEntity)))
            .andExpect(status().isBadRequest());

        // Validate the ProfilEntity in the database
        List<ProfilEntity> profilEntityList = profilEntityRepository.findAll();
        assertThat(profilEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProfilEntity in Elasticsearch
        verify(mockProfilEntitySearchRepository, times(0)).save(profilEntity);
    }

    @Test
    @Transactional
    public void deleteProfilEntity() throws Exception {
        // Initialize the database
        profilEntityRepository.saveAndFlush(profilEntity);

        int databaseSizeBeforeDelete = profilEntityRepository.findAll().size();

        // Delete the profilEntity
        restProfilEntityMockMvc.perform(delete("/api/profil-entities/{id}", profilEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfilEntity> profilEntityList = profilEntityRepository.findAll();
        assertThat(profilEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProfilEntity in Elasticsearch
        verify(mockProfilEntitySearchRepository, times(1)).deleteById(profilEntity.getId());
    }

    @Test
    @Transactional
    public void searchProfilEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        profilEntityRepository.saveAndFlush(profilEntity);
        when(mockProfilEntitySearchRepository.search(queryStringQuery("id:" + profilEntity.getId())))
            .thenReturn(Collections.singletonList(profilEntity));

        // Search the profilEntity
        restProfilEntityMockMvc.perform(get("/api/_search/profil-entities?query=id:" + profilEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profilEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].profilId").value(hasItem(DEFAULT_PROFIL_ID)))
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME)))
            .andExpect(jsonPath("$.[*].nachname").value(hasItem(DEFAULT_NACHNAME)));
    }
}
