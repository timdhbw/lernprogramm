package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.repository.entity.MultipleChoiceAntwortEntity;
import de.master.lernprogramm.repository.MultipleChoiceAntwortEntityRepository;
import de.master.lernprogramm.repository.search.MultipleChoiceAntwortEntitySearchRepository;
import de.master.lernprogramm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link MultipleChoiceAntwortEntity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MultipleChoiceAntwortEntityResource {

    private final Logger log = LoggerFactory.getLogger(MultipleChoiceAntwortEntityResource.class);

    private static final String ENTITY_NAME = "multipleChoiceAntwortEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MultipleChoiceAntwortEntityRepository multipleChoiceAntwortEntityRepository;

    private final MultipleChoiceAntwortEntitySearchRepository multipleChoiceAntwortEntitySearchRepository;

    public MultipleChoiceAntwortEntityResource(MultipleChoiceAntwortEntityRepository multipleChoiceAntwortEntityRepository, MultipleChoiceAntwortEntitySearchRepository multipleChoiceAntwortEntitySearchRepository) {
        this.multipleChoiceAntwortEntityRepository = multipleChoiceAntwortEntityRepository;
        this.multipleChoiceAntwortEntitySearchRepository = multipleChoiceAntwortEntitySearchRepository;
    }

    /**
     * {@code POST  /multiple-choice-antwort-entities} : Create a new multipleChoiceAntwortEntity.
     *
     * @param multipleChoiceAntwortEntity the multipleChoiceAntwortEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new multipleChoiceAntwortEntity, or with status {@code 400 (Bad Request)} if the multipleChoiceAntwortEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/multiple-choice-antwort-entities")
    public ResponseEntity<MultipleChoiceAntwortEntity> createMultipleChoiceAntwortEntity(@RequestBody MultipleChoiceAntwortEntity multipleChoiceAntwortEntity) throws URISyntaxException {
        log.debug("REST request to save MultipleChoiceAntwortEntity : {}", multipleChoiceAntwortEntity);
        if (multipleChoiceAntwortEntity.getId() != null) {
            throw new BadRequestAlertException("A new multipleChoiceAntwortEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MultipleChoiceAntwortEntity result = multipleChoiceAntwortEntityRepository.save(multipleChoiceAntwortEntity);
        multipleChoiceAntwortEntitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/multiple-choice-antwort-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /multiple-choice-antwort-entities} : Updates an existing multipleChoiceAntwortEntity.
     *
     * @param multipleChoiceAntwortEntity the multipleChoiceAntwortEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated multipleChoiceAntwortEntity,
     * or with status {@code 400 (Bad Request)} if the multipleChoiceAntwortEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the multipleChoiceAntwortEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/multiple-choice-antwort-entities")
    public ResponseEntity<MultipleChoiceAntwortEntity> updateMultipleChoiceAntwortEntity(@RequestBody MultipleChoiceAntwortEntity multipleChoiceAntwortEntity) throws URISyntaxException {
        log.debug("REST request to update MultipleChoiceAntwortEntity : {}", multipleChoiceAntwortEntity);
        if (multipleChoiceAntwortEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MultipleChoiceAntwortEntity result = multipleChoiceAntwortEntityRepository.save(multipleChoiceAntwortEntity);
        multipleChoiceAntwortEntitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, multipleChoiceAntwortEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /multiple-choice-antwort-entities} : get all the multipleChoiceAntwortEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of multipleChoiceAntwortEntities in body.
     */
    @GetMapping("/multiple-choice-antwort-entities")
    public List<MultipleChoiceAntwortEntity> getAllMultipleChoiceAntwortEntities() {
        log.debug("REST request to get all MultipleChoiceAntwortEntities");
        return multipleChoiceAntwortEntityRepository.findAll();
    }

    /**
     * {@code GET  /multiple-choice-antwort-entities/:id} : get the "id" multipleChoiceAntwortEntity.
     *
     * @param id the id of the multipleChoiceAntwortEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the multipleChoiceAntwortEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/multiple-choice-antwort-entities/{id}")
    public ResponseEntity<MultipleChoiceAntwortEntity> getMultipleChoiceAntwortEntity(@PathVariable Long id) {
        log.debug("REST request to get MultipleChoiceAntwortEntity : {}", id);
        Optional<MultipleChoiceAntwortEntity> multipleChoiceAntwortEntity = multipleChoiceAntwortEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(multipleChoiceAntwortEntity);
    }

    /**
     * {@code DELETE  /multiple-choice-antwort-entities/:id} : delete the "id" multipleChoiceAntwortEntity.
     *
     * @param id the id of the multipleChoiceAntwortEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/multiple-choice-antwort-entities/{id}")
    public ResponseEntity<Void> deleteMultipleChoiceAntwortEntity(@PathVariable Long id) {
        log.debug("REST request to delete MultipleChoiceAntwortEntity : {}", id);
        multipleChoiceAntwortEntityRepository.deleteById(id);
        multipleChoiceAntwortEntitySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/multiple-choice-antwort-entities?query=:query} : search for the multipleChoiceAntwortEntity corresponding
     * to the query.
     *
     * @param query the query of the multipleChoiceAntwortEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/multiple-choice-antwort-entities")
    public List<MultipleChoiceAntwortEntity> searchMultipleChoiceAntwortEntities(@RequestParam String query) {
        log.debug("REST request to search MultipleChoiceAntwortEntities for query {}", query);
        return StreamSupport
            .stream(multipleChoiceAntwortEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
