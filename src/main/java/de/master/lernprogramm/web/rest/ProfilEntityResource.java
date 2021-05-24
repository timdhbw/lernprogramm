package de.master.lernprogramm.web.rest;

import de.master.lernprogramm.repository.entity.ProfilEntity;
import de.master.lernprogramm.repository.ProfilEntityRepository;
import de.master.lernprogramm.repository.search.ProfilEntitySearchRepository;
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
 * REST controller for managing {@link ProfilEntity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProfilEntityResource {

    private final Logger log = LoggerFactory.getLogger(ProfilEntityResource.class);

    private static final String ENTITY_NAME = "profilEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfilEntityRepository profilEntityRepository;

    private final ProfilEntitySearchRepository profilEntitySearchRepository;

    public ProfilEntityResource(ProfilEntityRepository profilEntityRepository, ProfilEntitySearchRepository profilEntitySearchRepository) {
        this.profilEntityRepository = profilEntityRepository;
        this.profilEntitySearchRepository = profilEntitySearchRepository;
    }

    /**
     * {@code POST  /profil-entities} : Create a new profilEntity.
     *
     * @param profilEntity the profilEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profilEntity, or with status {@code 400 (Bad Request)} if the profilEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profil-entities")
    public ResponseEntity<ProfilEntity> createProfilEntity(@Valid @RequestBody ProfilEntity profilEntity) throws URISyntaxException {
        log.debug("REST request to save ProfilEntity : {}", profilEntity);
        if (profilEntity.getId() != null) {
            throw new BadRequestAlertException("A new profilEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfilEntity result = profilEntityRepository.save(profilEntity);
        profilEntitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/profil-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profil-entities} : Updates an existing profilEntity.
     *
     * @param profilEntity the profilEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profilEntity,
     * or with status {@code 400 (Bad Request)} if the profilEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profilEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profil-entities")
    public ResponseEntity<ProfilEntity> updateProfilEntity(@Valid @RequestBody ProfilEntity profilEntity) throws URISyntaxException {
        log.debug("REST request to update ProfilEntity : {}", profilEntity);
        if (profilEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfilEntity result = profilEntityRepository.save(profilEntity);
        profilEntitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profilEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profil-entities} : get all the profilEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profilEntities in body.
     */
    @GetMapping("/profil-entities")
    public List<ProfilEntity> getAllProfilEntities() {
        log.debug("REST request to get all ProfilEntities");
        return profilEntityRepository.findAll();
    }

    /**
     * {@code GET  /profil-entities/:id} : get the "id" profilEntity.
     *
     * @param id the id of the profilEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profilEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profil-entities/{id}")
    public ResponseEntity<ProfilEntity> getProfilEntity(@PathVariable Long id) {
        log.debug("REST request to get ProfilEntity : {}", id);
        Optional<ProfilEntity> profilEntity = profilEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(profilEntity);
    }

    /**
     * {@code DELETE  /profil-entities/:id} : delete the "id" profilEntity.
     *
     * @param id the id of the profilEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profil-entities/{id}")
    public ResponseEntity<Void> deleteProfilEntity(@PathVariable Long id) {
        log.debug("REST request to delete ProfilEntity : {}", id);
        profilEntityRepository.deleteById(id);
        profilEntitySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/profil-entities?query=:query} : search for the profilEntity corresponding
     * to the query.
     *
     * @param query the query of the profilEntity search.
     * @return the result of the search.
     */
    @GetMapping("/_search/profil-entities")
    public List<ProfilEntity> searchProfilEntities(@RequestParam String query) {
        log.debug("REST request to search ProfilEntities for query {}", query);
        return StreamSupport
            .stream(profilEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
