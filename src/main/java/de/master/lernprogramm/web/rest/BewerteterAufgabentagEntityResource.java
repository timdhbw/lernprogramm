package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.repository.entity.BewerteterAufgabentagEntity;
import de.master.lernprogramm.repository.BewerteterAufgabentagEntityRepository;
import de.master.lernprogramm.repository.search.BewerteterAufgabentagEntitySearchRepository;
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
 * REST controller for managing {@link BewerteterAufgabentagEntity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BewerteterAufgabentagEntityResource {

    private final Logger log = LoggerFactory.getLogger(BewerteterAufgabentagEntityResource.class);

    private static final String ENTITY_NAME = "bewerteterAufgabentagEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BewerteterAufgabentagEntityRepository bewerteterAufgabentagEntityRepository;

    private final BewerteterAufgabentagEntitySearchRepository bewerteterAufgabentagEntitySearchRepository;

    public BewerteterAufgabentagEntityResource(BewerteterAufgabentagEntityRepository bewerteterAufgabentagEntityRepository, BewerteterAufgabentagEntitySearchRepository bewerteterAufgabentagEntitySearchRepository) {
        this.bewerteterAufgabentagEntityRepository = bewerteterAufgabentagEntityRepository;
        this.bewerteterAufgabentagEntitySearchRepository = bewerteterAufgabentagEntitySearchRepository;
    }

    /**
     * {@code POST  /bewerteter-aufgabentag-entities} : Create a new bewerteterAufgabentagEntity.
     *
     * @param bewerteterAufgabentagEntity the bewerteterAufgabentagEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bewerteterAufgabentagEntity, or with status {@code 400 (Bad Request)} if the bewerteterAufgabentagEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bewerteter-aufgabentag-entities")
    public ResponseEntity<BewerteterAufgabentagEntity> createBewerteterAufgabentagEntity(@RequestBody BewerteterAufgabentagEntity bewerteterAufgabentagEntity) throws URISyntaxException {
        log.debug("REST request to save BewerteterAufgabentagEntity : {}", bewerteterAufgabentagEntity);
        if (bewerteterAufgabentagEntity.getId() != null) {
            throw new BadRequestAlertException("A new bewerteterAufgabentagEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BewerteterAufgabentagEntity result = bewerteterAufgabentagEntityRepository.save(bewerteterAufgabentagEntity);
        bewerteterAufgabentagEntitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bewerteter-aufgabentag-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bewerteter-aufgabentag-entities} : Updates an existing bewerteterAufgabentagEntity.
     *
     * @param bewerteterAufgabentagEntity the bewerteterAufgabentagEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bewerteterAufgabentagEntity,
     * or with status {@code 400 (Bad Request)} if the bewerteterAufgabentagEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bewerteterAufgabentagEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bewerteter-aufgabentag-entities")
    public ResponseEntity<BewerteterAufgabentagEntity> updateBewerteterAufgabentagEntity(@RequestBody BewerteterAufgabentagEntity bewerteterAufgabentagEntity) throws URISyntaxException {
        log.debug("REST request to update BewerteterAufgabentagEntity : {}", bewerteterAufgabentagEntity);
        if (bewerteterAufgabentagEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BewerteterAufgabentagEntity result = bewerteterAufgabentagEntityRepository.save(bewerteterAufgabentagEntity);
        bewerteterAufgabentagEntitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bewerteterAufgabentagEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bewerteter-aufgabentag-entities} : get all the bewerteterAufgabentagEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bewerteterAufgabentagEntities in body.
     */
    @GetMapping("/bewerteter-aufgabentag-entities")
    public List<BewerteterAufgabentagEntity> getAllBewerteterAufgabentagEntities() {
        log.debug("REST request to get all BewerteterAufgabentagEntities");
        return bewerteterAufgabentagEntityRepository.findAll();
    }

    /**
     * {@code GET  /bewerteter-aufgabentag-entities/:id} : get the "id" bewerteterAufgabentagEntity.
     *
     * @param id the id of the bewerteterAufgabentagEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bewerteterAufgabentagEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bewerteter-aufgabentag-entities/{id}")
    public ResponseEntity<BewerteterAufgabentagEntity> getBewerteterAufgabentagEntity(@PathVariable Long id) {
        log.debug("REST request to get BewerteterAufgabentagEntity : {}", id);
        Optional<BewerteterAufgabentagEntity> bewerteterAufgabentagEntity = bewerteterAufgabentagEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bewerteterAufgabentagEntity);
    }

    /**
     * {@code DELETE  /bewerteter-aufgabentag-entities/:id} : delete the "id" bewerteterAufgabentagEntity.
     *
     * @param id the id of the bewerteterAufgabentagEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bewerteter-aufgabentag-entities/{id}")
    public ResponseEntity<Void> deleteBewerteterAufgabentagEntity(@PathVariable Long id) {
        log.debug("REST request to delete BewerteterAufgabentagEntity : {}", id);
        bewerteterAufgabentagEntityRepository.deleteById(id);
        bewerteterAufgabentagEntitySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/bewerteter-aufgabentag-entities?query=:query} : search for the bewerteterAufgabentagEntity corresponding
     * to the query.
     *
     * @param query the query of the bewerteterAufgabentagEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/bewerteter-aufgabentag-entities")
    public List<BewerteterAufgabentagEntity> searchBewerteterAufgabentagEntities(@RequestParam String query) {
        log.debug("REST request to search BewerteterAufgabentagEntities for query {}", query);
        return StreamSupport
            .stream(bewerteterAufgabentagEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
