package de.master.lernprogramm.repository;

import de.master.lernprogramm.repository.entity.ProfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProfilEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilEntityRepository extends JpaRepository<ProfilEntity, Long> {

    ProfilEntity findFirstByProfilId(String profilId);
}
