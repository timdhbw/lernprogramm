package de.master.lernprogramm.repository.search;

import de.master.lernprogramm.repository.entity.AufgabentagEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AufgabentagEntity} entity.
 */
public interface AufgabentagEntitySearchRepository extends ElasticsearchRepository<AufgabentagEntity, Long> {
}
