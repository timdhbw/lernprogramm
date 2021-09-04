package de.master.lernprogramm.domain;

import de.master.lernprogramm.repository.entity.AufgabenbwtunghistEntity;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.master.lernprogramm.web.rest.TestUtil;

public class AufgabenbwtunghistEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AufgabenbwtunghistEntity.class);
        AufgabenbwtunghistEntity aufgabenbwtunghistEntity1 = new AufgabenbwtunghistEntity();
        aufgabenbwtunghistEntity1.setId(1L);
        AufgabenbwtunghistEntity aufgabenbwtunghistEntity2 = new AufgabenbwtunghistEntity();
        aufgabenbwtunghistEntity2.setId(aufgabenbwtunghistEntity1.getId());
        assertThat(aufgabenbwtunghistEntity1).isEqualTo(aufgabenbwtunghistEntity2);
        aufgabenbwtunghistEntity2.setId(2L);
        assertThat(aufgabenbwtunghistEntity1).isNotEqualTo(aufgabenbwtunghistEntity2);
        aufgabenbwtunghistEntity1.setId(null);
        assertThat(aufgabenbwtunghistEntity1).isNotEqualTo(aufgabenbwtunghistEntity2);
    }
}
