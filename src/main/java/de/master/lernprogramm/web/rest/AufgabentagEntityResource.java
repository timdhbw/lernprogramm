package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.repository.entity.AufgabentagEntity;
import de.master.lernprogramm.repository.AufgabentagEntityRepository;
import de.master.lernprogramm.repository.search.AufgabentagEntitySearchRepository;
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
 * REST controller for managing {@link AufgabentagEntity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AufgabentagEntityResource {

    private final Logger log = LoggerFactory.getLogger(AufgabentagEntityResource.class);

    private static final String ENTITY_NAME = "aufgabentagEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AufgabentagEntityRepository aufgabentagEntityRepository;

    private final AufgabentagEntitySearchRepository aufgabentagEntitySearchRepository;

    public AufgabentagEntityResource(AufgabentagEntityRepository aufgabentagEntityRepository, AufgabentagEntitySearchRepository aufgabentagEntitySearchRepository) {
        this.aufgabentagEntityRepository = aufgabentagEntityRepository;
        this.aufgabentagEntitySearchRepository = aufgabentagEntitySearchRepository;
    }

    /**
     * {@code POST  /aufgabentag-entities} : Create a new aufgabentagEntity.
     *
     * @param aufgabentagEntity the aufgabentagEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aufgabentagEntity, or with status {@code 400 (Bad Request)} if the aufgabentagEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aufgabentag-entities")
    public ResponseEntity<AufgabentagEntity> createAufgabentagEntity(@Valid @RequestBody AufgabentagEntity aufgabentagEntity) throws URISyntaxException {
        log.debug("REST request to save AufgabentagEntity : {}", aufgabentagEntity);
        if (aufgabentagEntity.getId() != null) {
            throw new BadRequestAlertException("A new aufgabentagEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AufgabentagEntity result = aufgabentagEntityRepository.save(aufgabentagEntity);
        aufgabentagEntitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aufgabentag-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aufgabentag-entities} : Updates an existing aufgabentagEntity.
     *
     * @param aufgabentagEntity the aufgabentagEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aufgabentagEntity,
     * or with status {@code 400 (Bad Request)} if the aufgabentagEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aufgabentagEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aufgabentag-entities")
    public ResponseEntity<AufgabentagEntity> updateAufgabentagEntity(@Valid @RequestBody AufgabentagEntity aufgabentagEntity) throws URISyntaxException {
        log.debug("REST request to update AufgabentagEntity : {}", aufgabentagEntity);
        if (aufgabentagEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AufgabentagEntity result = aufgabentagEntityRepository.save(aufgabentagEntity);
        aufgabentagEntitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aufgabentagEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aufgabentag-entities} : get all the aufgabentagEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aufgabentagEntities in body.
     */
    @GetMapping("/aufgabentag-entities")
    public List<AufgabentagEntity> getAllAufgabentagEntities() {
        log.debug("REST request to get all AufgabentagEntities");
        return aufgabentagEntityRepository.findAll();
    }

    /**
     * {@code GET  /aufgabentag-entities/:id} : get the "id" aufgabentagEntity.
     *
     * @param id the id of the aufgabentagEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aufgabentagEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aufgabentag-entities/{id}")
    public ResponseEntity<AufgabentagEntity> getAufgabentagEntity(@PathVariable Long id) {
        log.debug("REST request to get AufgabentagEntity : {}", id);
        Optional<AufgabentagEntity> aufgabentagEntity = aufgabentagEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aufgabentagEntity);
    }

    /**
     * {@code DELETE  /aufgabentag-entities/:id} : delete the "id" aufgabentagEntity.
     *
     * @param id the id of the aufgabentagEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aufgabentag-entities/{id}")
    public ResponseEntity<Void> deleteAufgabentagEntity(@PathVariable Long id) {
        log.debug("REST request to delete AufgabentagEntity : {}", id);
        aufgabentagEntityRepository.deleteById(id);
        aufgabentagEntitySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/aufgabentag-entities?query=:query} : search for the aufgabentagEntity corresponding
     * to the query.
     *
     * @param query the query of the aufgabentagEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/aufgabentag-entities")
    public List<AufgabentagEntity> searchAufgabentagEntities(@RequestParam String query) {
        log.debug("REST request to search AufgabentagEntities for query {}", query);
        return StreamSupport
            .stream(aufgabentagEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
