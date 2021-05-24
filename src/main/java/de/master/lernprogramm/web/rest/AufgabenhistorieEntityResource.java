package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.repository.entity.AufgabenhistorieEntity;
import de.master.lernprogramm.repository.AufgabenhistorieEntityRepository;
import de.master.lernprogramm.repository.search.AufgabenhistorieEntitySearchRepository;
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
 * REST controller for managing {@link AufgabenhistorieEntity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AufgabenhistorieEntityResource {

    private final Logger log = LoggerFactory.getLogger(AufgabenhistorieEntityResource.class);

    private static final String ENTITY_NAME = "aufgabenhistorieEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AufgabenhistorieEntityRepository aufgabenhistorieEntityRepository;

    private final AufgabenhistorieEntitySearchRepository aufgabenhistorieEntitySearchRepository;

    public AufgabenhistorieEntityResource(AufgabenhistorieEntityRepository aufgabenhistorieEntityRepository, AufgabenhistorieEntitySearchRepository aufgabenhistorieEntitySearchRepository) {
        this.aufgabenhistorieEntityRepository = aufgabenhistorieEntityRepository;
        this.aufgabenhistorieEntitySearchRepository = aufgabenhistorieEntitySearchRepository;
    }

    /**
     * {@code POST  /aufgabenhistorie-entities} : Create a new aufgabenhistorieEntity.
     *
     * @param aufgabenhistorieEntity the aufgabenhistorieEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aufgabenhistorieEntity, or with status {@code 400 (Bad Request)} if the aufgabenhistorieEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aufgabenhistorie-entities")
    public ResponseEntity<AufgabenhistorieEntity> createAufgabenhistorieEntity(@RequestBody AufgabenhistorieEntity aufgabenhistorieEntity) throws URISyntaxException {
        log.debug("REST request to save AufgabenhistorieEntity : {}", aufgabenhistorieEntity);
        if (aufgabenhistorieEntity.getId() != null) {
            throw new BadRequestAlertException("A new aufgabenhistorieEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AufgabenhistorieEntity result = aufgabenhistorieEntityRepository.save(aufgabenhistorieEntity);
        aufgabenhistorieEntitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aufgabenhistorie-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aufgabenhistorie-entities} : Updates an existing aufgabenhistorieEntity.
     *
     * @param aufgabenhistorieEntity the aufgabenhistorieEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aufgabenhistorieEntity,
     * or with status {@code 400 (Bad Request)} if the aufgabenhistorieEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aufgabenhistorieEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aufgabenhistorie-entities")
    public ResponseEntity<AufgabenhistorieEntity> updateAufgabenhistorieEntity(@RequestBody AufgabenhistorieEntity aufgabenhistorieEntity) throws URISyntaxException {
        log.debug("REST request to update AufgabenhistorieEntity : {}", aufgabenhistorieEntity);
        if (aufgabenhistorieEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AufgabenhistorieEntity result = aufgabenhistorieEntityRepository.save(aufgabenhistorieEntity);
        aufgabenhistorieEntitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aufgabenhistorieEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aufgabenhistorie-entities} : get all the aufgabenhistorieEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aufgabenhistorieEntities in body.
     */
    @GetMapping("/aufgabenhistorie-entities")
    public List<AufgabenhistorieEntity> getAllAufgabenhistorieEntities() {
        log.debug("REST request to get all AufgabenhistorieEntities");
        return aufgabenhistorieEntityRepository.findAll();
    }

    /**
     * {@code GET  /aufgabenhistorie-entities/:id} : get the "id" aufgabenhistorieEntity.
     *
     * @param id the id of the aufgabenhistorieEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aufgabenhistorieEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aufgabenhistorie-entities/{id}")
    public ResponseEntity<AufgabenhistorieEntity> getAufgabenhistorieEntity(@PathVariable Long id) {
        log.debug("REST request to get AufgabenhistorieEntity : {}", id);
        Optional<AufgabenhistorieEntity> aufgabenhistorieEntity = aufgabenhistorieEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aufgabenhistorieEntity);
    }

    /**
     * {@code DELETE  /aufgabenhistorie-entities/:id} : delete the "id" aufgabenhistorieEntity.
     *
     * @param id the id of the aufgabenhistorieEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aufgabenhistorie-entities/{id}")
    public ResponseEntity<Void> deleteAufgabenhistorieEntity(@PathVariable Long id) {
        log.debug("REST request to delete AufgabenhistorieEntity : {}", id);
        aufgabenhistorieEntityRepository.deleteById(id);
        aufgabenhistorieEntitySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/aufgabenhistorie-entities?query=:query} : search for the aufgabenhistorieEntity corresponding
     * to the query.
     *
     * @param query the query of the aufgabenhistorieEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/aufgabenhistorie-entities")
    public List<AufgabenhistorieEntity> searchAufgabenhistorieEntities(@RequestParam String query) {
        log.debug("REST request to search AufgabenhistorieEntities for query {}", query);
        return StreamSupport
            .stream(aufgabenhistorieEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
