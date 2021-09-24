package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.AufgabenbewertungHistorie;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.repository.entity.AufgabeEntity;
import de.master.lernprogramm.repository.entity.AufgabenbwtunghistEntity;
import de.master.lernprogramm.repository.entity.AufgabenteilEntity;
import de.master.lernprogramm.repository.mapper.AufgabeEntityMapper;
import de.master.lernprogramm.repository.mapper.ProfilEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AufgabeRepositoryImpl implements AufgabeRepository{

    private final AufgabeEntityRepository aufgabeEntityRepository;

    private final ProfilRepository profilRepository;

    private final AufgabeEntityMapper aufgabeEntityMapper;

    private final ProfilEntityMapper profilEntityMapper;

    private final AufgabenteilEntityRepository aufgabenteilEntityRepository;

    private final AufgabenbwtunghistEntityRepository aufgabenbwtunghistEntityRepository;

    public AufgabeRepositoryImpl(AufgabeEntityRepository aufgabeEntityRepository, ProfilRepository profilRepository, AufgabeEntityMapper aufgabeEntityMapper, ProfilEntityMapper profilEntityMapper, AufgabenteilEntityRepository aufgabenteilEntityRepository, AufgabenbwtunghistEntityRepository aufgabenbwtunghistEntityRepository) {
        this.aufgabeEntityRepository = aufgabeEntityRepository;
        this.profilRepository = profilRepository;
        this.aufgabeEntityMapper = aufgabeEntityMapper;
        this.profilEntityMapper = profilEntityMapper;
        this.aufgabenteilEntityRepository = aufgabenteilEntityRepository;
        this.aufgabenbwtunghistEntityRepository = aufgabenbwtunghistEntityRepository;
    }

    @Override
    public Aufgabe saveAufgabe(Aufgabe aufgabeToSave) {
        if (aufgabeToSave == null) {
            return null;
        }
        Profil profil = profilRepository.getProfilByProfilId(aufgabeToSave.getAutorId().toString());
        AufgabeEntity aufgabeEntityToSave = aufgabeEntityMapper.toEntity(aufgabeToSave);
        aufgabeEntityToSave.setAutor(profilEntityMapper.toEntity(profil));
        aufgabeEntityToSave.getAufgabenteilEntities().forEach(this::saveAufgabenteile);
        return aufgabeEntityMapper.toDomain(aufgabeEntityRepository.save(aufgabeEntityToSave));
    }

    @Override
    public List<Integer> getAllPossibleAufgabenIds() {
        return aufgabeEntityRepository.getAllIds()
            .stream()
            .map(Long::intValue)
            .collect(Collectors.toList());
    }

    @Override
    public Aufgabe getAufgabeById(Integer aufgabeId) {
        if (aufgabeId == null) {
            log.error("AufgabeId ist null");
            return null;
        }
        return aufgabeEntityMapper.toDomain(aufgabeEntityRepository
            .findById(aufgabeId.longValue()).orElse(null));
    }

    @Override
    public void setAufgabeBewertung(Integer aufgabeId, AufgabenbewertungHistorie aufgabenbewertungHistorie) {
        AufgabenbwtunghistEntity aufgabenbwtunghistEntity = aufgabeEntityMapper.toEntity(aufgabenbewertungHistorie);
        aufgabenbwtunghistEntity.setAufgabe(aufgabeEntityRepository.findById(aufgabeId.longValue()).orElse(null));
        aufgabenbwtunghistEntityRepository.save(aufgabenbwtunghistEntity);

    }

    private void saveAufgabenteile(AufgabenteilEntity aufgabenteilEntity) {
        aufgabenteilEntity = aufgabenteilEntityRepository.save(aufgabenteilEntity);
    }
}
