package de.master.lernprogramm.repository.search;

import de.master.lernprogramm.repository.entity.AufgabenhistorieEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AufgabenhistorieEntity} entity.
 */
public interface AufgabenhistorieEntitySearchRepository extends ElasticsearchRepository<AufgabenhistorieEntity, Long> {
}
