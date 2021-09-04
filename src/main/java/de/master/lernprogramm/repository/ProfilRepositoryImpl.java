package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.repository.mapper.ProfilEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfilRepositoryImpl implements ProfilRepository{

    private final ProfilEntityRepository profilEntityRepository;

    private final ProfilEntityMapper profilEntityMapper;

    public ProfilRepositoryImpl(ProfilEntityRepository profilEntityRepository, ProfilEntityMapper profilEntityMapper) {
        this.profilEntityRepository = profilEntityRepository;
        this.profilEntityMapper = profilEntityMapper;
    }

    @Override
    public Profil getProfilByProfilId(String profilId) {
        if (profilId == null) {
            return null;
        }
        return profilEntityMapper.toDomain(profilEntityRepository.findFirstByProfilId(profilId));
    }
}
