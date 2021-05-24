package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.repository.entity.AufgabenteilEntity;
import de.master.lernprogramm.repository.AufgabenteilEntityRepository;
import de.master.lernprogramm.repository.search.AufgabenteilEntitySearchRepository;
import de.master.lernprogramm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link AufgabenteilEntity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AufgabenteilEntityResource {

    private final Logger log = LoggerFactory.getLogger(AufgabenteilEntityResource.class);

    private static final String ENTITY_NAME = "aufgabenteilEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AufgabenteilEntityRepository aufgabenteilEntityRepository;

    private final AufgabenteilEntitySearchRepository aufgabenteilEntitySearchRepository;

    public AufgabenteilEntityResource(AufgabenteilEntityRepository aufgabenteilEntityRepository, AufgabenteilEntitySearchRepository aufgabenteilEntitySearchRepository) {
        this.aufgabenteilEntityRepository = aufgabenteilEntityRepository;
        this.aufgabenteilEntitySearchRepository = aufgabenteilEntitySearchRepository;
    }

    /**
     * {@code POST  /aufgabenteil-entities} : Create a new aufgabenteilEntity.
     *
     * @param aufgabenteilEntity the aufgabenteilEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aufgabenteilEntity, or with status {@code 400 (Bad Request)} if the aufgabenteilEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aufgabenteil-entities")
    public ResponseEntity<AufgabenteilEntity> createAufgabenteilEntity(@Valid @RequestBody AufgabenteilEntity aufgabenteilEntity) throws URISyntaxException {
        log.debug("REST request to save AufgabenteilEntity : {}", aufgabenteilEntity);
        if (aufgabenteilEntity.getId() != null) {
            throw new BadRequestAlertException("A new aufgabenteilEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AufgabenteilEntity result = aufgabenteilEntityRepository.save(aufgabenteilEntity);
        aufgabenteilEntitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aufgabenteil-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aufgabenteil-entities} : Updates an existing aufgabenteilEntity.
     *
     * @param aufgabenteilEntity the aufgabenteilEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aufgabenteilEntity,
     * or with status {@code 400 (Bad Request)} if the aufgabenteilEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aufgabenteilEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aufgabenteil-entities")
    public ResponseEntity<AufgabenteilEntity> updateAufgabenteilEntity(@Valid @RequestBody AufgabenteilEntity aufgabenteilEntity) throws URISyntaxException {
        log.debug("REST request to update AufgabenteilEntity : {}", aufgabenteilEntity);
        if (aufgabenteilEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AufgabenteilEntity result = aufgabenteilEntityRepository.save(aufgabenteilEntity);
        aufgabenteilEntitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aufgabenteilEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aufgabenteil-entities} : get all the aufgabenteilEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aufgabenteilEntities in body.
     */
    @GetMapping("/aufgabenteil-entities")
    public List<AufgabenteilEntity> getAllAufgabenteilEntities() {
        log.debug("REST request to get all AufgabenteilEntities");
        return aufgabenteilEntityRepository.findAll();
    }

    /**
     * {@code GET  /aufgabenteil-entities/:id} : get the "id" aufgabenteilEntity.
     *
     * @param id the id of the aufgabenteilEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aufgabenteilEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aufgabenteil-entities/{id}")
    public ResponseEntity<AufgabenteilEntity> getAufgabenteilEntity(@PathVariable Long id) {
        log.debug("REST request to get AufgabenteilEntity : {}", id);
        Optional<AufgabenteilEntity> aufgabenteilEntity = aufgabenteilEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aufgabenteilEntity);
    }

    /**
     * {@code DELETE  /aufgabenteil-entities/:id} : delete the "id" aufgabenteilEntity.
     *
     * @param id the id of the aufgabenteilEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aufgabenteil-entities/{id}")
    public ResponseEntity<Void> deleteAufgabenteilEntity(@PathVariable Long id) {
        log.debug("REST request to delete AufgabenteilEntity : {}", id);
        aufgabenteilEntityRepository.deleteById(id);
        aufgabenteilEntitySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/aufgabenteil-entities?query=:query} : search for the aufgabenteilEntity corresponding
     * to the query.
     *
     * @param query the query of the aufgabenteilEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/aufgabenteil-entities")
    public List<AufgabenteilEntity> searchAufgabenteilEntities(@RequestParam String query) {
        log.debug("REST request to search AufgabenteilEntities for query {}", query);
        return StreamSupport
            .stream(aufgabenteilEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
