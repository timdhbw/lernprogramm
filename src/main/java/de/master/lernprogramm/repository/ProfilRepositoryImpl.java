package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.repository.mapper.ProfilEntityMapper;
import org.springframework.stereotype.Service;

@Service
public class ProfilRepositoryImpl implements ProfilRepository{

    private final ProfilEntityRepository profilEntityRepository;

    private final ProfilEntityMapper profilEntityMapper;

    public ProfilRepositoryImpl(ProfilEntityRepository profilEntityRepository, ProfilEntityMapper profilEntityMapper) {
        this.profilEntityRepository = profilEntityRepository;
        this.profilEntityMapper = profilEntityMapper;
    }

    @Override
    public Profil getProfilById(Integer profilId) {
        if (profilId == null) {
            return null;
        }
        return profilEntityMapper.toDomain(profilEntityRepository.findById(profilId.longValue()).orElse(null));
    }
}
