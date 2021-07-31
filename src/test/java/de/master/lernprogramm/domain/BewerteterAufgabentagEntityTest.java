package de.master.lernprogramm.domain;

import de.master.lernprogramm.repository.entity.BewerteterAufgabentagEntity;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.master.lernprogramm.web.rest.TestUtil;

public class BewerteterAufgabentagEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BewerteterAufgabentagEntity.class);
        BewerteterAufgabentagEntity bewerteterAufgabentagEntity1 = new BewerteterAufgabentagEntity();
        bewerteterAufgabentagEntity1.setId(1L);
        BewerteterAufgabentagEntity bewerteterAufgabentagEntity2 = new BewerteterAufgabentagEntity();
        bewerteterAufgabentagEntity2.setId(bewerteterAufgabentagEntity1.getId());
        assertThat(bewerteterAufgabentagEntity1).isEqualTo(bewerteterAufgabentagEntity2);
        bewerteterAufgabentagEntity2.setId(2L);
        assertThat(bewerteterAufgabentagEntity1).isNotEqualTo(bewerteterAufgabentagEntity2);
        bewerteterAufgabentagEntity1.setId(null);
        assertThat(bewerteterAufgabentagEntity1).isNotEqualTo(bewerteterAufgabentagEntity2);
    }
}
