package de.master.lernprogramm.domain.service;

import de.master.lernprogramm.domain.objekt.Aufgabenhistorie;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.repository.AufgabeRepository;
import de.master.lernprogramm.repository.ProfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class ProfilServiceImpl implements ProfilService {

    private final PunkteberechnungService punkteberechnungService;

    private final ProfilRepository profilRepository;

    private final AufgabeRepository aufgabeRepository;

    public ProfilServiceImpl(PunkteberechnungService punkteberechnungService, ProfilRepository profilRepository, AufgabeRepository aufgabeRepository) {
        this.punkteberechnungService = punkteberechnungService;
        this.profilRepository = profilRepository;
        this.aufgabeRepository = aufgabeRepository;
    }

    @Override
    public Profil getProfilById(String profilId) {
        if (profilId == null) {
            return null;
        }
        log.info("Suche Profil für ProfilId: '{}'", profilId);
        Profil profil = profilRepository.getProfilByProfilId(profilId);
//        List<Aufgabe> aufgabeList = aufgabeRepository.getAufgabeById()
        log.info("Gefundenes Profil für ProfilId '{}' : {}", profilId, profil);
//        profil.setProfilId(profilId);
//        profil.setNachname("vName" + profilId);
//        profil.setVorname("vVorname" + profilId);
//        punkteberechnungService.berechnePunkteFuerProfil(profil);
        return profil;
    }

    @Override
    public Profil saveProfil(Profil profilToSave) {
        if (profilToSave == null) {
            return null;
        }
        return profilRepository.saveProfil(profilToSave);
    }

    @Override
    public void setAufgabeVonProfilAbgeschlossen(Integer userId, Integer aufgabeId, double ergebnisUser) {

        Aufgabenhistorie aufgabenhistorie = new Aufgabenhistorie();
        aufgabenhistorie.setDatum(LocalDate.now());
        aufgabenhistorie.setBewertungsveraenderung(ergebnisUser);
        profilRepository.addAufgabenhistorie(userId, aufgabeId, aufgabenhistorie);
        log.info("Aufgabenhistoriesi von ProfilId: {} nach: {}", userId, profilRepository.getProfilByProfilId(userId.toString()).getAufgabenhistorieList());
    }
}
