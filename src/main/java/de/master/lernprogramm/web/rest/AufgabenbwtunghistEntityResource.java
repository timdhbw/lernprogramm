package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.repository.entity.AufgabenbwtunghistEntity;
import de.master.lernprogramm.repository.AufgabenbwtunghistEntityRepository;
import de.master.lernprogramm.repository.search.AufgabenbwtunghistEntitySearchRepository;
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
 * REST controller for managing {@link AufgabenbwtunghistEntity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AufgabenbwtunghistEntityResource {

    private final Logger log = LoggerFactory.getLogger(AufgabenbwtunghistEntityResource.class);

    private static final String ENTITY_NAME = "aufgabenbwtunghistEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AufgabenbwtunghistEntityRepository aufgabenbwtunghistEntityRepository;

    private final AufgabenbwtunghistEntitySearchRepository aufgabenbwtunghistEntitySearchRepository;

    public AufgabenbwtunghistEntityResource(AufgabenbwtunghistEntityRepository aufgabenbwtunghistEntityRepository, AufgabenbwtunghistEntitySearchRepository aufgabenbwtunghistEntitySearchRepository) {
        this.aufgabenbwtunghistEntityRepository = aufgabenbwtunghistEntityRepository;
        this.aufgabenbwtunghistEntitySearchRepository = aufgabenbwtunghistEntitySearchRepository;
    }

    /**
     * {@code POST  /aufgabenbwtunghist-entities} : Create a new aufgabenbwtunghistEntity.
     *
     * @param aufgabenbwtunghistEntity the aufgabenbwtunghistEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aufgabenbwtunghistEntity, or with status {@code 400 (Bad Request)} if the aufgabenbwtunghistEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aufgabenbwtunghist-entities")
    public ResponseEntity<AufgabenbwtunghistEntity> createAufgabenbwtunghistEntity(@RequestBody AufgabenbwtunghistEntity aufgabenbwtunghistEntity) throws URISyntaxException {
        log.debug("REST request to save AufgabenbwtunghistEntity : {}", aufgabenbwtunghistEntity);
        if (aufgabenbwtunghistEntity.getId() != null) {
            throw new BadRequestAlertException("A new aufgabenbwtunghistEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AufgabenbwtunghistEntity result = aufgabenbwtunghistEntityRepository.save(aufgabenbwtunghistEntity);
        aufgabenbwtunghistEntitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aufgabenbwtunghist-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aufgabenbwtunghist-entities} : Updates an existing aufgabenbwtunghistEntity.
     *
     * @param aufgabenbwtunghistEntity the aufgabenbwtunghistEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aufgabenbwtunghistEntity,
     * or with status {@code 400 (Bad Request)} if the aufgabenbwtunghistEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aufgabenbwtunghistEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aufgabenbwtunghist-entities")
    public ResponseEntity<AufgabenbwtunghistEntity> updateAufgabenbwtunghistEntity(@RequestBody AufgabenbwtunghistEntity aufgabenbwtunghistEntity) throws URISyntaxException {
        log.debug("REST request to update AufgabenbwtunghistEntity : {}", aufgabenbwtunghistEntity);
        if (aufgabenbwtunghistEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AufgabenbwtunghistEntity result = aufgabenbwtunghistEntityRepository.save(aufgabenbwtunghistEntity);
        aufgabenbwtunghistEntitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aufgabenbwtunghistEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aufgabenbwtunghist-entities} : get all the aufgabenbwtunghistEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aufgabenbwtunghistEntities in body.
     */
    @GetMapping("/aufgabenbwtunghist-entities")
    public List<AufgabenbwtunghistEntity> getAllAufgabenbwtunghistEntities() {
        log.debug("REST request to get all AufgabenbwtunghistEntities");
        return aufgabenbwtunghistEntityRepository.findAll();
    }

    /**
     * {@code GET  /aufgabenbwtunghist-entities/:id} : get the "id" aufgabenbwtunghistEntity.
     *
     * @param id the id of the aufgabenbwtunghistEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aufgabenbwtunghistEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aufgabenbwtunghist-entities/{id}")
    public ResponseEntity<AufgabenbwtunghistEntity> getAufgabenbwtunghistEntity(@PathVariable Long id) {
        log.debug("REST request to get AufgabenbwtunghistEntity : {}", id);
        Optional<AufgabenbwtunghistEntity> aufgabenbwtunghistEntity = aufgabenbwtunghistEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aufgabenbwtunghistEntity);
    }

    /**
     * {@code DELETE  /aufgabenbwtunghist-entities/:id} : delete the "id" aufgabenbwtunghistEntity.
     *
     * @param id the id of the aufgabenbwtunghistEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aufgabenbwtunghist-entities/{id}")
    public ResponseEntity<Void> deleteAufgabenbwtunghistEntity(@PathVariable Long id) {
        log.debug("REST request to delete AufgabenbwtunghistEntity : {}", id);
        aufgabenbwtunghistEntityRepository.deleteById(id);
        aufgabenbwtunghistEntitySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/aufgabenbwtunghist-entities?query=:query} : search for the aufgabenbwtunghistEntity corresponding
     * to the query.
     *
     * @param query the query of the aufgabenbwtunghistEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/aufgabenbwtunghist-entities")
    public List<AufgabenbwtunghistEntity> searchAufgabenbwtunghistEntities(@RequestParam String query) {
        log.debug("REST request to search AufgabenbwtunghistEntities for query {}", query);
        return StreamSupport
            .stream(aufgabenbwtunghistEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
