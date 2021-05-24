package de.master.lernprogramm.repository;

import de.master.lernprogramm.repository.entity.AufgabentagEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AufgabentagEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AufgabentagEntityRepository extends JpaRepository<AufgabentagEntity, Long> {
}
