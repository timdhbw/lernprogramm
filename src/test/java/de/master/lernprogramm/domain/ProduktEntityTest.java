package de.master.lernprogramm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.master.lernprogramm.web.rest.TestUtil;

public class ProduktEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduktEntity.class);
        ProduktEntity produktEntity1 = new ProduktEntity();
        produktEntity1.setId(1L);
        ProduktEntity produktEntity2 = new ProduktEntity();
        produktEntity2.setId(produktEntity1.getId());
        assertThat(produktEntity1).isEqualTo(produktEntity2);
        produktEntity2.setId(2L);
        assertThat(produktEntity1).isNotEqualTo(produktEntity2);
        produktEntity1.setId(null);
        assertThat(produktEntity1).isNotEqualTo(produktEntity2);
    }
}
