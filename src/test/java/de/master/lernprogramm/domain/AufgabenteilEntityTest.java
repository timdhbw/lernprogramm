package de.master.lernprogramm.domain;

import de.master.lernprogramm.repository.entity.AufgabenteilEntity;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.master.lernprogramm.web.rest.TestUtil;

public class AufgabenteilEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AufgabenteilEntity.class);
        AufgabenteilEntity aufgabenteilEntity1 = new AufgabenteilEntity();
        aufgabenteilEntity1.setId(1L);
        AufgabenteilEntity aufgabenteilEntity2 = new AufgabenteilEntity();
        aufgabenteilEntity2.setId(aufgabenteilEntity1.getId());
        assertThat(aufgabenteilEntity1).isEqualTo(aufgabenteilEntity2);
        aufgabenteilEntity2.setId(2L);
        assertThat(aufgabenteilEntity1).isNotEqualTo(aufgabenteilEntity2);
        aufgabenteilEntity1.setId(null);
        assertThat(aufgabenteilEntity1).isNotEqualTo(aufgabenteilEntity2);
    }
}
