package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.repository.entity.AufgabeEntity;
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

    public AufgabeRepositoryImpl(AufgabeEntityRepository aufgabeEntityRepository, ProfilRepository profilRepository, AufgabeEntityMapper aufgabeEntityMapper, ProfilEntityMapper profilEntityMapper, AufgabenteilEntityRepository aufgabenteilEntityRepository) {
        this.aufgabeEntityRepository = aufgabeEntityRepository;
        this.profilRepository = profilRepository;
        this.aufgabeEntityMapper = aufgabeEntityMapper;
        this.profilEntityMapper = profilEntityMapper;
        this.aufgabenteilEntityRepository = aufgabenteilEntityRepository;
    }

    @Override
    public Aufgabe saveAufgabe(Aufgabe aufgabeToSave) {
        if (aufgabeToSave == null) {
            return null;
        }
        Profil profil = profilRepository.getProfilById(aufgabeToSave.getAutorId());
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

    private void saveAufgabenteile(AufgabenteilEntity aufgabenteilEntity) {
        aufgabenteilEntity = aufgabenteilEntityRepository.save(aufgabenteilEntity);
    }
}
