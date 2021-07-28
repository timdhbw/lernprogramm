package de.master.lernprogramm.repository;

import de.master.lernprogramm.repository.entity.AufgabeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AufgabeEntity entity.
 */
@Repository
public interface AufgabeEntityRepository extends JpaRepository<AufgabeEntity, Long> {

    @Query(value = "select distinct aufgabeEntity from AufgabeEntity aufgabeEntity left join fetch aufgabeEntity.aufgabentags",
        countQuery = "select count(distinct aufgabeEntity) from AufgabeEntity aufgabeEntity")
    Page<AufgabeEntity> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct aufgabeEntity from AufgabeEntity aufgabeEntity left join fetch aufgabeEntity.aufgabentags")
    List<AufgabeEntity> findAllWithEagerRelationships();

    @Query("select aufgabeEntity from AufgabeEntity aufgabeEntity left join fetch aufgabeEntity.aufgabentags where aufgabeEntity.id =:id")
    Optional<AufgabeEntity> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select aufgabeEntity.id from AufgabeEntity aufgabeEntity")
    List<Long> getAllIds();
}
