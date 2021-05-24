package de.master.lernprogramm.repository;

import de.master.lernprogramm.repository.entity.AufgabenhistorieEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AufgabenhistorieEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AufgabenhistorieEntityRepository extends JpaRepository<AufgabenhistorieEntity, Long> {
}
