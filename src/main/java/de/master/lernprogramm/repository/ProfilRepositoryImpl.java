package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Aufgabenhistorie;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.repository.entity.AufgabenhistorieEntity;
import de.master.lernprogramm.repository.mapper.ProfilEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfilRepositoryImpl implements ProfilRepository{

    private final ProfilEntityRepository profilEntityRepository;

    private final ProfilEntityMapper profilEntityMapper;

    private final AufgabeEntityRepository aufgabeEntityRepository;

    private final AufgabenhistorieEntityRepository aufgabenhistorieEntityRepository;

    private final AufgabentagEntityRepository aufgabentagEntityRepository;

    public ProfilRepositoryImpl(ProfilEntityRepository profilEntityRepository, ProfilEntityMapper profilEntityMapper, AufgabeEntityRepository aufgabeEntityRepository, AufgabenhistorieEntityRepository aufgabenhistorieEntityRepository, AufgabentagEntityRepository aufgabentagEntityRepository) {
        this.profilEntityRepository = profilEntityRepository;
        this.profilEntityMapper = profilEntityMapper;
        this.aufgabeEntityRepository = aufgabeEntityRepository;
        this.aufgabenhistorieEntityRepository = aufgabenhistorieEntityRepository;
        this.aufgabentagEntityRepository = aufgabentagEntityRepository;
    }

    @Override
    public Profil getProfilByProfilId(String profilId) {
        if (profilId == null) {
            return null;
        }
        Profil profil = profilEntityMapper.toDomain(profilEntityRepository.findFirstByProfilId(profilId));
        profil.setAllPossibleTagList(profilEntityMapper.toAufgabentagDomainList(aufgabentagEntityRepository.findAll()));
        return profil;
    }

    @Override
    public void addAufgabenhistorie(Integer userId, Integer aufgabeId, Aufgabenhistorie aufgabenhistorie) {
        AufgabenhistorieEntity aufgabenhistorieEntity = profilEntityMapper.toEntity(aufgabenhistorie);
        aufgabenhistorieEntity.aufgabe(aufgabeEntityRepository.findById(aufgabeId.longValue()).orElse(null));
        aufgabenhistorieEntity.setProfil(profilEntityRepository.findFirstByProfilId(userId.toString()));
        aufgabenhistorieEntityRepository.save(aufgabenhistorieEntity);
    }

    @Override
    public Profil saveProfil(Profil profilToSave) {
        return profilEntityMapper.toDomain(profilEntityRepository.save(profilEntityMapper.toEntity(profilToSave)));
    }
}
