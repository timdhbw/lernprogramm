package de.master.lernprogramm.domain;

import de.master.lernprogramm.repository.entity.AufgabentagEntity;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.master.lernprogramm.web.rest.TestUtil;

public class AufgabentagEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AufgabentagEntity.class);
        AufgabentagEntity aufgabentagEntity1 = new AufgabentagEntity();
        aufgabentagEntity1.setId(1L);
        AufgabentagEntity aufgabentagEntity2 = new AufgabentagEntity();
        aufgabentagEntity2.setId(aufgabentagEntity1.getId());
        assertThat(aufgabentagEntity1).isEqualTo(aufgabentagEntity2);
        aufgabentagEntity2.setId(2L);
        assertThat(aufgabentagEntity1).isNotEqualTo(aufgabentagEntity2);
        aufgabentagEntity1.setId(null);
        assertThat(aufgabentagEntity1).isNotEqualTo(aufgabentagEntity2);
    }
}
