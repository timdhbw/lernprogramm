package de.master.lernprogramm.repository.search;

import de.master.lernprogramm.repository.entity.ProfilEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProfilEntity} entity.
 */
public interface ProfilEntitySearchRepository extends ElasticsearchRepository<ProfilEntity, Long> {
}
