package de.master.lernprogramm.repository.search;

import de.master.lernprogramm.repository.entity.MultipleChoiceAntwortEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MultipleChoiceAntwortEntity} entity.
 */
public interface MultipleChoiceAntwortEntitySearchRepository extends ElasticsearchRepository<MultipleChoiceAntwortEntity, Long> {
}
