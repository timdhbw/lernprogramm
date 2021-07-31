package de.master.lernprogramm.domain;

import de.master.lernprogramm.repository.entity.ProfilEntity;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.master.lernprogramm.web.rest.TestUtil;

public class ProfilEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfilEntity.class);
        ProfilEntity profilEntity1 = new ProfilEntity();
        profilEntity1.setId(1L);
        ProfilEntity profilEntity2 = new ProfilEntity();
        profilEntity2.setId(profilEntity1.getId());
        assertThat(profilEntity1).isEqualTo(profilEntity2);
        profilEntity2.setId(2L);
        assertThat(profilEntity1).isNotEqualTo(profilEntity2);
        profilEntity1.setId(null);
        assertThat(profilEntity1).isNotEqualTo(profilEntity2);
    }
}
