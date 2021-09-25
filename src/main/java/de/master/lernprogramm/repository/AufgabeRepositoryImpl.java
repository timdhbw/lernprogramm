package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.AufgabenbewertungHistorie;
import de.master.lernprogramm.domain.objekt.Aufgabentag;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.repository.entity.AufgabeEntity;
import de.master.lernprogramm.repository.entity.AufgabenbwtunghistEntity;
import de.master.lernprogramm.repository.entity.AufgabentagEntity;
import de.master.lernprogramm.repository.entity.AufgabenteilEntity;
import de.master.lernprogramm.repository.mapper.AufgabeEntityMapper;
import de.master.lernprogramm.repository.mapper.ProfilEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AufgabeRepositoryImpl implements AufgabeRepository {

    private final AufgabeEntityRepository aufgabeEntityRepository;

    private final ProfilRepository profilRepository;

    private final AufgabeEntityMapper aufgabeEntityMapper;

    private final ProfilEntityMapper profilEntityMapper;

    private final AufgabenteilEntityRepository aufgabenteilEntityRepository;

    private final AufgabenbwtunghistEntityRepository aufgabenbwtunghistEntityRepository;

    private final AufgabentagEntityRepository aufgabentagEntityRepository;

    public AufgabeRepositoryImpl(AufgabeEntityRepository aufgabeEntityRepository, ProfilRepository profilRepository, AufgabeEntityMapper aufgabeEntityMapper, ProfilEntityMapper profilEntityMapper, AufgabenteilEntityRepository aufgabenteilEntityRepository, AufgabenbwtunghistEntityRepository aufgabenbwtunghistEntityRepository, AufgabentagEntityRepository aufgabentagEntityRepository) {
        this.aufgabeEntityRepository = aufgabeEntityRepository;
        this.profilRepository = profilRepository;
        this.aufgabeEntityMapper = aufgabeEntityMapper;
        this.profilEntityMapper = profilEntityMapper;
        this.aufgabenteilEntityRepository = aufgabenteilEntityRepository;
        this.aufgabenbwtunghistEntityRepository = aufgabenbwtunghistEntityRepository;
        this.aufgabentagEntityRepository = aufgabentagEntityRepository;
    }

    @Override
    public Aufgabe saveAufgabe(Aufgabe aufgabeToSave) {
        if (aufgabeToSave == null) {
            return null;
        }
        log.info("Versuche Aufgabe zu speichern: {}", aufgabeToSave);
        Profil profil = profilRepository.getProfilByProfilId(aufgabeToSave.getAutorId().toString());
        log.info("Profil geholt");
        AufgabeEntity aufgabeEntityToSave = aufgabeEntityMapper.toEntity(aufgabeToSave);
        log.info("Mapped to entity");
        aufgabeEntityToSave.setAutor(profilEntityMapper.toEntity(profil));
        aufgabeEntityToSave.getAufgabenteilEntities().forEach(this::saveAufgabenteile);
        log.info("Aufgabenteile gespeichert");
        AufgabeEntity aufgabeEntity = aufgabeEntityRepository.save(aufgabeEntityToSave);
        log.info("Aufgabe gespeichert: {}", aufgabeEntity);
        saveAufgabentagsAnAufgabe(aufgabeEntity, aufgabeToSave.getAufgabentagList());
        log.info("Aufgabentags gespeichert");
        return aufgabeEntityMapper.toDomain(aufgabeEntityRepository.findById(aufgabeEntity.getId()).orElse(null));
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

    private void saveAufgabentagsAnAufgabe(AufgabeEntity aufgabeEntity, List<Aufgabentag> aufgabentagListToSave) {
        List<AufgabentagEntity> exitstingAufgabentags = aufgabentagEntityRepository.findAll();
        aufgabentagListToSave.forEach(tagToSave -> {
                AufgabentagEntity aufgabentagEntity = exitstingAufgabentags.stream()
                    .filter(existTag -> tagToSave.getTag().equals(existTag.getTag()))
                    .findAny()
                    .orElse(saveNewAufgabentagEntity(tagToSave.getTag()));
                aufgabentagEntity.addAufgabe(aufgabeEntity);
                aufgabentagEntityRepository.save(aufgabentagEntity);
            }
        );
    }

    private AufgabentagEntity saveNewAufgabentagEntity(String tagToSave) {
        AufgabentagEntity toSave = new AufgabentagEntity()
            .tag(tagToSave)
            .text(tagToSave);
        return aufgabentagEntityRepository.save(toSave);
    }

    private void saveAufgabenteile(AufgabenteilEntity aufgabenteilEntity) {
        aufgabenteilEntity = aufgabenteilEntityRepository.save(aufgabenteilEntity);
    }
}
