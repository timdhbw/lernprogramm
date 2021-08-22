package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.LernprogrammApp;
import de.master.lernprogramm.repository.entity.MultipleChoiceAntwortEntity;
import de.master.lernprogramm.repository.MultipleChoiceAntwortEntityRepository;
import de.master.lernprogramm.repository.search.MultipleChoiceAntwortEntitySearchRepository;

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
 * Integration tests for the {@link MultipleChoiceAntwortEntityResource} REST controller.
 */
@SpringBootTest(classes = LernprogrammApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MultipleChoiceAntwortEntityResourceIT {

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final Boolean DEFAULT_CHECKED_RICHTIG = false;
    private static final Boolean UPDATED_CHECKED_RICHTIG = true;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private MultipleChoiceAntwortEntityRepository multipleChoiceAntwortEntityRepository;

    /**
     * This repository is mocked in the de.master.lernprogramm.repository.search test package.
     *
     * @see de.master.lernprogramm.repository.search.MultipleChoiceAntwortEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private MultipleChoiceAntwortEntitySearchRepository mockMultipleChoiceAntwortEntitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMultipleChoiceAntwortEntityMockMvc;

    private MultipleChoiceAntwortEntity multipleChoiceAntwortEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MultipleChoiceAntwortEntity createEntity(EntityManager em) {
        MultipleChoiceAntwortEntity multipleChoiceAntwortEntity = new MultipleChoiceAntwortEntity()
            .checked(DEFAULT_CHECKED)
            .checkedRichtig(DEFAULT_CHECKED_RICHTIG)
            .label(DEFAULT_LABEL);
        return multipleChoiceAntwortEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MultipleChoiceAntwortEntity createUpdatedEntity(EntityManager em) {
        MultipleChoiceAntwortEntity multipleChoiceAntwortEntity = new MultipleChoiceAntwortEntity()
            .checked(UPDATED_CHECKED)
            .checkedRichtig(UPDATED_CHECKED_RICHTIG)
            .label(UPDATED_LABEL);
        return multipleChoiceAntwortEntity;
    }

    @BeforeEach
    public void initTest() {
        multipleChoiceAntwortEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createMultipleChoiceAntwortEntity() throws Exception {
        int databaseSizeBeforeCreate = multipleChoiceAntwortEntityRepository.findAll().size();
        // Create the MultipleChoiceAntwortEntity
        restMultipleChoiceAntwortEntityMockMvc.perform(post("/api/multiple-choice-antwort-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(multipleChoiceAntwortEntity)))
            .andExpect(status().isCreated());

        // Validate the MultipleChoiceAntwortEntity in the database
        List<MultipleChoiceAntwortEntity> multipleChoiceAntwortEntityList = multipleChoiceAntwortEntityRepository.findAll();
        assertThat(multipleChoiceAntwortEntityList).hasSize(databaseSizeBeforeCreate + 1);
        MultipleChoiceAntwortEntity testMultipleChoiceAntwortEntity = multipleChoiceAntwortEntityList.get(multipleChoiceAntwortEntityList.size() - 1);
        assertThat(testMultipleChoiceAntwortEntity.isChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testMultipleChoiceAntwortEntity.isCheckedRichtig()).isEqualTo(DEFAULT_CHECKED_RICHTIG);
        assertThat(testMultipleChoiceAntwortEntity.getLabel()).isEqualTo(DEFAULT_LABEL);

        // Validate the MultipleChoiceAntwortEntity in Elasticsearch
        verify(mockMultipleChoiceAntwortEntitySearchRepository, times(1)).save(testMultipleChoiceAntwortEntity);
    }

    @Test
    @Transactional
    public void createMultipleChoiceAntwortEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = multipleChoiceAntwortEntityRepository.findAll().size();

        // Create the MultipleChoiceAntwortEntity with an existing ID
        multipleChoiceAntwortEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMultipleChoiceAntwortEntityMockMvc.perform(post("/api/multiple-choice-antwort-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(multipleChoiceAntwortEntity)))
            .andExpect(status().isBadRequest());

        // Validate the MultipleChoiceAntwortEntity in the database
        List<MultipleChoiceAntwortEntity> multipleChoiceAntwortEntityList = multipleChoiceAntwortEntityRepository.findAll();
        assertThat(multipleChoiceAntwortEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the MultipleChoiceAntwortEntity in Elasticsearch
        verify(mockMultipleChoiceAntwortEntitySearchRepository, times(0)).save(multipleChoiceAntwortEntity);
    }


    @Test
    @Transactional
    public void getAllMultipleChoiceAntwortEntities() throws Exception {
        // Initialize the database
        multipleChoiceAntwortEntityRepository.saveAndFlush(multipleChoiceAntwortEntity);

        // Get all the multipleChoiceAntwortEntityList
        restMultipleChoiceAntwortEntityMockMvc.perform(get("/api/multiple-choice-antwort-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(multipleChoiceAntwortEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].checkedRichtig").value(hasItem(DEFAULT_CHECKED_RICHTIG.booleanValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    public void getMultipleChoiceAntwortEntity() throws Exception {
        // Initialize the database
        multipleChoiceAntwortEntityRepository.saveAndFlush(multipleChoiceAntwortEntity);

        // Get the multipleChoiceAntwortEntity
        restMultipleChoiceAntwortEntityMockMvc.perform(get("/api/multiple-choice-antwort-entities/{id}", multipleChoiceAntwortEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(multipleChoiceAntwortEntity.getId().intValue()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.checkedRichtig").value(DEFAULT_CHECKED_RICHTIG.booleanValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }
    @Test
    @Transactional
    public void getNonExistingMultipleChoiceAntwortEntity() throws Exception {
        // Get the multipleChoiceAntwortEntity
        restMultipleChoiceAntwortEntityMockMvc.perform(get("/api/multiple-choice-antwort-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMultipleChoiceAntwortEntity() throws Exception {
        // Initialize the database
        multipleChoiceAntwortEntityRepository.saveAndFlush(multipleChoiceAntwortEntity);

        int databaseSizeBeforeUpdate = multipleChoiceAntwortEntityRepository.findAll().size();

        // Update the multipleChoiceAntwortEntity
        MultipleChoiceAntwortEntity updatedMultipleChoiceAntwortEntity = multipleChoiceAntwortEntityRepository.findById(multipleChoiceAntwortEntity.getId()).get();
        // Disconnect from session so that the updates on updatedMultipleChoiceAntwortEntity are not directly saved in db
        em.detach(updatedMultipleChoiceAntwortEntity);
        updatedMultipleChoiceAntwortEntity
            .checked(UPDATED_CHECKED)
            .checkedRichtig(UPDATED_CHECKED_RICHTIG)
            .label(UPDATED_LABEL);

        restMultipleChoiceAntwortEntityMockMvc.perform(put("/api/multiple-choice-antwort-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMultipleChoiceAntwortEntity)))
            .andExpect(status().isOk());

        // Validate the MultipleChoiceAntwortEntity in the database
        List<MultipleChoiceAntwortEntity> multipleChoiceAntwortEntityList = multipleChoiceAntwortEntityRepository.findAll();
        assertThat(multipleChoiceAntwortEntityList).hasSize(databaseSizeBeforeUpdate);
        MultipleChoiceAntwortEntity testMultipleChoiceAntwortEntity = multipleChoiceAntwortEntityList.get(multipleChoiceAntwortEntityList.size() - 1);
        assertThat(testMultipleChoiceAntwortEntity.isChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testMultipleChoiceAntwortEntity.isCheckedRichtig()).isEqualTo(UPDATED_CHECKED_RICHTIG);
        assertThat(testMultipleChoiceAntwortEntity.getLabel()).isEqualTo(UPDATED_LABEL);

        // Validate the MultipleChoiceAntwortEntity in Elasticsearch
        verify(mockMultipleChoiceAntwortEntitySearchRepository, times(1)).save(testMultipleChoiceAntwortEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingMultipleChoiceAntwortEntity() throws Exception {
        int databaseSizeBeforeUpdate = multipleChoiceAntwortEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMultipleChoiceAntwortEntityMockMvc.perform(put("/api/multiple-choice-antwort-entities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(multipleChoiceAntwortEntity)))
            .andExpect(status().isBadRequest());

        // Validate the MultipleChoiceAntwortEntity in the database
        List<MultipleChoiceAntwortEntity> multipleChoiceAntwortEntityList = multipleChoiceAntwortEntityRepository.findAll();
        assertThat(multipleChoiceAntwortEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MultipleChoiceAntwortEntity in Elasticsearch
        verify(mockMultipleChoiceAntwortEntitySearchRepository, times(0)).save(multipleChoiceAntwortEntity);
    }

    @Test
    @Transactional
    public void deleteMultipleChoiceAntwortEntity() throws Exception {
        // Initialize the database
        multipleChoiceAntwortEntityRepository.saveAndFlush(multipleChoiceAntwortEntity);

        int databaseSizeBeforeDelete = multipleChoiceAntwortEntityRepository.findAll().size();

        // Delete the multipleChoiceAntwortEntity
        restMultipleChoiceAntwortEntityMockMvc.perform(delete("/api/multiple-choice-antwort-entities/{id}", multipleChoiceAntwortEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MultipleChoiceAntwortEntity> multipleChoiceAntwortEntityList = multipleChoiceAntwortEntityRepository.findAll();
        assertThat(multipleChoiceAntwortEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MultipleChoiceAntwortEntity in Elasticsearch
        verify(mockMultipleChoiceAntwortEntitySearchRepository, times(1)).deleteById(multipleChoiceAntwortEntity.getId());
    }

    @Test
    @Transactional
    public void searchMultipleChoiceAntwortEntity() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        multipleChoiceAntwortEntityRepository.saveAndFlush(multipleChoiceAntwortEntity);
        when(mockMultipleChoiceAntwortEntitySearchRepository.search(queryStringQuery("id:" + multipleChoiceAntwortEntity.getId())))
            .thenReturn(Collections.singletonList(multipleChoiceAntwortEntity));

        // Search the multipleChoiceAntwortEntity
        restMultipleChoiceAntwortEntityMockMvc.perform(get("/api/_search/multiple-choice-antwort-entities?query=id:" + multipleChoiceAntwortEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(multipleChoiceAntwortEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].checkedRichtig").value(hasItem(DEFAULT_CHECKED_RICHTIG.booleanValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }
}
