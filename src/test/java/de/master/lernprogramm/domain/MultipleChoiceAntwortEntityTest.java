package de.master.lernprogramm.domain;

import de.master.lernprogramm.repository.entity.MultipleChoiceAntwortEntity;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.master.lernprogramm.web.rest.TestUtil;

public class MultipleChoiceAntwortEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MultipleChoiceAntwortEntity.class);
        MultipleChoiceAntwortEntity multipleChoiceAntwortEntity1 = new MultipleChoiceAntwortEntity();
        multipleChoiceAntwortEntity1.setId(1L);
        MultipleChoiceAntwortEntity multipleChoiceAntwortEntity2 = new MultipleChoiceAntwortEntity();
        multipleChoiceAntwortEntity2.setId(multipleChoiceAntwortEntity1.getId());
        assertThat(multipleChoiceAntwortEntity1).isEqualTo(multipleChoiceAntwortEntity2);
        multipleChoiceAntwortEntity2.setId(2L);
        assertThat(multipleChoiceAntwortEntity1).isNotEqualTo(multipleChoiceAntwortEntity2);
        multipleChoiceAntwortEntity1.setId(null);
        assertThat(multipleChoiceAntwortEntity1).isNotEqualTo(multipleChoiceAntwortEntity2);
    }
}
