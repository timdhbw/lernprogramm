package de.master.lernprogramm.domain.service;

import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.Aufgabenhistorie;
import de.master.lernprogramm.domain.objekt.Profil;
import de.master.lernprogramm.repository.AufgabeRepository;
import de.master.lernprogramm.repository.ProfilRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        Profil profil = new Profil();
        profil.setProfilId(profilId);
        profil.setNachname("vName" + profilId);
        profil.setVorname("vVorname" + profilId);
//        punkteberechnungService.berechnePunkteFuerProfil(profil);
        return profil;
    }

    @Override
    public void setAufgabeVonProfilAbgeschlossen(Integer userId, Integer aufgabeId, Integer ergebnisUser) {
        Profil profil = profilRepository.getProfilById(userId);
        Aufgabe aufgabe = aufgabeRepository.getAufgabeById(aufgabeId);

        Aufgabenhistorie aufgabenhistorie = new Aufgabenhistorie();
        aufgabenhistorie.setAufgabe(aufgabe);
        aufgabenhistorie.setDatum(LocalDate.now());
        aufgabenhistorie.setBewertungsveraenderung(ergebnisUser);
        profil.addAufgabenhistorie(aufgabenhistorie);
    }
}
