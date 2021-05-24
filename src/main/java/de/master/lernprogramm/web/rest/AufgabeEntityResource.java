package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.repository.entity.AufgabeEntity;
import de.master.lernprogramm.repository.AufgabeEntityRepository;
import de.master.lernprogramm.repository.search.AufgabeEntitySearchRepository;
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
 * REST controller for managing {@link AufgabeEntity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AufgabeEntityResource {

    private final Logger log = LoggerFactory.getLogger(AufgabeEntityResource.class);

    private static final String ENTITY_NAME = "aufgabeEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AufgabeEntityRepository aufgabeEntityRepository;

    private final AufgabeEntitySearchRepository aufgabeEntitySearchRepository;

    public AufgabeEntityResource(AufgabeEntityRepository aufgabeEntityRepository, AufgabeEntitySearchRepository aufgabeEntitySearchRepository) {
        this.aufgabeEntityRepository = aufgabeEntityRepository;
        this.aufgabeEntitySearchRepository = aufgabeEntitySearchRepository;
    }

    /**
     * {@code POST  /aufgabe-entities} : Create a new aufgabeEntity.
     *
     * @param aufgabeEntity the aufgabeEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aufgabeEntity, or with status {@code 400 (Bad Request)} if the aufgabeEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aufgabe-entities")
    public ResponseEntity<AufgabeEntity> createAufgabeEntity(@Valid @RequestBody AufgabeEntity aufgabeEntity) throws URISyntaxException {
        log.debug("REST request to save AufgabeEntity : {}", aufgabeEntity);
        if (aufgabeEntity.getId() != null) {
            throw new BadRequestAlertException("A new aufgabeEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AufgabeEntity result = aufgabeEntityRepository.save(aufgabeEntity);
        aufgabeEntitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aufgabe-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aufgabe-entities} : Updates an existing aufgabeEntity.
     *
     * @param aufgabeEntity the aufgabeEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aufgabeEntity,
     * or with status {@code 400 (Bad Request)} if the aufgabeEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aufgabeEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aufgabe-entities")
    public ResponseEntity<AufgabeEntity> updateAufgabeEntity(@Valid @RequestBody AufgabeEntity aufgabeEntity) throws URISyntaxException {
        log.debug("REST request to update AufgabeEntity : {}", aufgabeEntity);
        if (aufgabeEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AufgabeEntity result = aufgabeEntityRepository.save(aufgabeEntity);
        aufgabeEntitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aufgabeEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aufgabe-entities} : get all the aufgabeEntities.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aufgabeEntities in body.
     */
    @GetMapping("/aufgabe-entities")
    public List<AufgabeEntity> getAllAufgabeEntities(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all AufgabeEntities");
        return aufgabeEntityRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /aufgabe-entities/:id} : get the "id" aufgabeEntity.
     *
     * @param id the id of the aufgabeEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aufgabeEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aufgabe-entities/{id}")
    public ResponseEntity<AufgabeEntity> getAufgabeEntity(@PathVariable Long id) {
        log.debug("REST request to get AufgabeEntity : {}", id);
        Optional<AufgabeEntity> aufgabeEntity = aufgabeEntityRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(aufgabeEntity);
    }

    /**
     * {@code DELETE  /aufgabe-entities/:id} : delete the "id" aufgabeEntity.
     *
     * @param id the id of the aufgabeEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aufgabe-entities/{id}")
    public ResponseEntity<Void> deleteAufgabeEntity(@PathVariable Long id) {
        log.debug("REST request to delete AufgabeEntity : {}", id);
        aufgabeEntityRepository.deleteById(id);
        aufgabeEntitySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/aufgabe-entities?query=:query} : search for the aufgabeEntity corresponding
     * to the query.
     *
     * @param query the query of the aufgabeEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/aufgabe-entities")
    public List<AufgabeEntity> searchAufgabeEntities(@RequestParam String query) {
        log.debug("REST request to search AufgabeEntities for query {}", query);
        return StreamSupport
            .stream(aufgabeEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
