package de.master.lernprogramm.repository;

import de.master.lernprogramm.repository.entity.AufgabenteilEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AufgabenteilEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AufgabenteilEntityRepository extends JpaRepository<AufgabenteilEntity, Long> {
}
