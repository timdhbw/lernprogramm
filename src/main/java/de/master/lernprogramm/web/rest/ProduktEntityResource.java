package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.domain.ProduktEntity;
import de.master.lernprogramm.repository.ProduktEntityRepository;
import de.master.lernprogramm.repository.search.ProduktEntitySearchRepository;
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
 * REST controller for managing {@link de.master.lernprogramm.domain.ProduktEntity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProduktEntityResource {

    private final Logger log = LoggerFactory.getLogger(ProduktEntityResource.class);

    private static final String ENTITY_NAME = "produktEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduktEntityRepository produktEntityRepository;

    private final ProduktEntitySearchRepository produktEntitySearchRepository;

    public ProduktEntityResource(ProduktEntityRepository produktEntityRepository, ProduktEntitySearchRepository produktEntitySearchRepository) {
        this.produktEntityRepository = produktEntityRepository;
        this.produktEntitySearchRepository = produktEntitySearchRepository;
    }

    /**
     * {@code POST  /produkt-entities} : Create a new produktEntity.
     *
     * @param produktEntity the produktEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produktEntity, or with status {@code 400 (Bad Request)} if the produktEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produkt-entities")
    public ResponseEntity<ProduktEntity> createProduktEntity(@RequestBody ProduktEntity produktEntity) throws URISyntaxException {
        log.debug("REST request to save ProduktEntity : {}", produktEntity);
        if (produktEntity.getId() != null) {
            throw new BadRequestAlertException("A new produktEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduktEntity result = produktEntityRepository.save(produktEntity);
        produktEntitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/produkt-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produkt-entities} : Updates an existing produktEntity.
     *
     * @param produktEntity the produktEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produktEntity,
     * or with status {@code 400 (Bad Request)} if the produktEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produktEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produkt-entities")
    public ResponseEntity<ProduktEntity> updateProduktEntity(@RequestBody ProduktEntity produktEntity) throws URISyntaxException {
        log.debug("REST request to update ProduktEntity : {}", produktEntity);
        if (produktEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProduktEntity result = produktEntityRepository.save(produktEntity);
        produktEntitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produktEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /produkt-entities} : get all the produktEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produktEntities in body.
     */
    @GetMapping("/produkt-entities")
    public List<ProduktEntity> getAllProduktEntities() {
        log.debug("REST request to get all ProduktEntities");
        return produktEntityRepository.findAll();
    }

    /**
     * {@code GET  /produkt-entities/:id} : get the "id" produktEntity.
     *
     * @param id the id of the produktEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produktEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produkt-entities/{id}")
    public ResponseEntity<ProduktEntity> getProduktEntity(@PathVariable Long id) {
        log.debug("REST request to get ProduktEntity : {}", id);
        Optional<ProduktEntity> produktEntity = produktEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(produktEntity);
    }

    /**
     * {@code DELETE  /produkt-entities/:id} : delete the "id" produktEntity.
     *
     * @param id the id of the produktEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produkt-entities/{id}")
    public ResponseEntity<Void> deleteProduktEntity(@PathVariable Long id) {
        log.debug("REST request to delete ProduktEntity : {}", id);
        produktEntityRepository.deleteById(id);
        produktEntitySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/produkt-entities?query=:query} : search for the produktEntity corresponding
     * to the query.
     *
     * @param query the query of the produktEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/produkt-entities")
    public List<ProduktEntity> searchProduktEntities(@RequestParam String query) {
        log.debug("REST request to search ProduktEntities for query {}", query);
        return StreamSupport
            .stream(produktEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
