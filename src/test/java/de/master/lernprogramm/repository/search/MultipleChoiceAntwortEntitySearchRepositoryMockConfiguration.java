package de.master.lernprogramm.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MultipleChoiceAntwortEntitySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MultipleChoiceAntwortEntitySearchRepositoryMockConfiguration {

    @MockBean
    private MultipleChoiceAntwortEntitySearchRepository mockMultipleChoiceAntwortEntitySearchRepository;

}
