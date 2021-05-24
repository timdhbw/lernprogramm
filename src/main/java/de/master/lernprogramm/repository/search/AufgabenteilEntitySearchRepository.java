package de.master.lernprogramm.repository.search;

import de.master.lernprogramm.repository.entity.AufgabenteilEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AufgabenteilEntity} entity.
 */
public interface AufgabenteilEntitySearchRepository extends ElasticsearchRepository<AufgabenteilEntity, Long> {
}
