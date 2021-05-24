package de.master.lernprogramm.repository;

import de.master.lernprogramm.repository.entity.BewerteterAufgabentagEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BewerteterAufgabentagEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BewerteterAufgabentagEntityRepository extends JpaRepository<BewerteterAufgabentagEntity, Long> {
}
