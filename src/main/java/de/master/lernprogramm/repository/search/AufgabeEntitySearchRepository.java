package de.master.lernprogramm.repository.search;

import de.master.lernprogramm.repository.entity.AufgabeEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AufgabeEntity} entity.
 */
public interface AufgabeEntitySearchRepository extends ElasticsearchRepository<AufgabeEntity, Long> {
}
