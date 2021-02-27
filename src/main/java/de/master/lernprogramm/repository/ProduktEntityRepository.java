package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.ProduktEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProduktEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduktEntityRepository extends JpaRepository<ProduktEntity, Long> {
}
