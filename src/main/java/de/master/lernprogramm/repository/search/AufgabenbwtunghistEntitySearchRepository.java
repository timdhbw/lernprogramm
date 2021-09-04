package de.master.lernprogramm.repository.search;

import de.master.lernprogramm.repository.entity.AufgabenbwtunghistEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AufgabenbwtunghistEntity} entity.
 */
public interface AufgabenbwtunghistEntitySearchRepository extends ElasticsearchRepository<AufgabenbwtunghistEntity, Long> {
}
