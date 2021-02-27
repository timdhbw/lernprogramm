package de.master.lernprogramm.repository.search;

import de.master.lernprogramm.domain.ProduktEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProduktEntity} entity.
 */
public interface ProduktEntitySearchRepository extends ElasticsearchRepository<ProduktEntity, Long> {
}
