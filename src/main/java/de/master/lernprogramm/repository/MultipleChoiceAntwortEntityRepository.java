package de.master.lernprogramm.repository;

import de.master.lernprogramm.repository.entity.MultipleChoiceAntwortEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MultipleChoiceAntwortEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MultipleChoiceAntwortEntityRepository extends JpaRepository<MultipleChoiceAntwortEntity, Long> {
}
