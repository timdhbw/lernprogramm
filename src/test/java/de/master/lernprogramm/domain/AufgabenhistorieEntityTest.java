package de.master.lernprogramm.domain;

import de.master.lernprogramm.repository.entity.AufgabenhistorieEntity;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.master.lernprogramm.web.rest.TestUtil;

public class AufgabenhistorieEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AufgabenhistorieEntity.class);
        AufgabenhistorieEntity aufgabenhistorieEntity1 = new AufgabenhistorieEntity();
        aufgabenhistorieEntity1.setId(1L);
        AufgabenhistorieEntity aufgabenhistorieEntity2 = new AufgabenhistorieEntity();
        aufgabenhistorieEntity2.setId(aufgabenhistorieEntity1.getId());
        assertThat(aufgabenhistorieEntity1).isEqualTo(aufgabenhistorieEntity2);
        aufgabenhistorieEntity2.setId(2L);
        assertThat(aufgabenhistorieEntity1).isNotEqualTo(aufgabenhistorieEntity2);
        aufgabenhistorieEntity1.setId(null);
        assertThat(aufgabenhistorieEntity1).isNotEqualTo(aufgabenhistorieEntity2);
    }
}
