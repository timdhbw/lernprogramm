package de.master.lernprogramm.repository;

import de.master.lernprogramm.repository.entity.AufgabenbwtunghistEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AufgabenbwtunghistEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AufgabenbwtunghistEntityRepository extends JpaRepository<AufgabenbwtunghistEntity, Long> {
}
