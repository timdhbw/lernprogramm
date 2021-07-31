package de.master.lernprogramm.domain;

import de.master.lernprogramm.repository.entity.AufgabeEntity;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.master.lernprogramm.web.rest.TestUtil;

public class AufgabeEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AufgabeEntity.class);
        AufgabeEntity aufgabeEntity1 = new AufgabeEntity();
        aufgabeEntity1.setId(1L);
        AufgabeEntity aufgabeEntity2 = new AufgabeEntity();
        aufgabeEntity2.setId(aufgabeEntity1.getId());
        assertThat(aufgabeEntity1).isEqualTo(aufgabeEntity2);
        aufgabeEntity2.setId(2L);
        assertThat(aufgabeEntity1).isNotEqualTo(aufgabeEntity2);
        aufgabeEntity1.setId(null);
        assertThat(aufgabeEntity1).isNotEqualTo(aufgabeEntity2);
    }
}
