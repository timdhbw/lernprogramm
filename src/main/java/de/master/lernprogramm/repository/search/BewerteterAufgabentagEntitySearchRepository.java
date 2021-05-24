package de.master.lernprogramm.repository.search;

import de.master.lernprogramm.repository.entity.BewerteterAufgabentagEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link BewerteterAufgabentagEntity} entity.
 */
public interface BewerteterAufgabentagEntitySearchRepository extends ElasticsearchRepository<BewerteterAufgabentagEntity, Long> {
}
