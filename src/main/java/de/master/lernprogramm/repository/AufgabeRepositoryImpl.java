package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.repository.entity.AufgabeEntity;
import de.master.lernprogramm.repository.mapper.AufgabeEntityMapper;
import de.master.lernprogramm.repository.mapper.ProfilEntityMapper;
import org.springframework.stereotype.Service;

@Service
public class AufgabeRepositoryImpl implements AufgabeRepository{

    private final AufgabeEntityRepository aufgabeEntityRepository;

    private final ProfilRepository profilRepository;

    private final AufgabeEntityMapper aufgabeEntityMapper;

    private final ProfilEntityMapper profilEntityMapper;

    public AufgabeRepositoryImpl(AufgabeEntityRepository aufgabeEntityRepository, ProfilRepository profilRepository, AufgabeEntityMapper aufgabeEntityMapper, ProfilEntityMapper profilEntityMapper) {
        this.aufgabeEntityRepository = aufgabeEntityRepository;
        this.profilRepository = profilRepository;
        this.aufgabeEntityMapper = aufgabeEntityMapper;
        this.profilEntityMapper = profilEntityMapper;
    }

    @Override
    public Aufgabe saveAufgabe(Aufgabe aufgabeToSave) {
        if (aufgabeToSave == null) {
            return null;
        }
        Profil profil = profilRepository.getProfilById(aufgabeToSave.getAutorId());
        AufgabeEntity aufgabeEntityToSave = aufgabeEntityMapper.toEntity(aufgabeToSave);
        aufgabeEntityToSave.setAutor(profilEntityMapper.toEntity(profil));
        return aufgabeEntityMapper.toDomain(aufgabeEntityRepository.save(aufgabeEntityToSave));
    }
}
