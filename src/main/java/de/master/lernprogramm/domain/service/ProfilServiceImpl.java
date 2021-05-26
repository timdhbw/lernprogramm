package de.master.lernprogramm.domain.service;

import de.master.lernprogramm.domain.objekt.Profil;
import org.springframework.stereotype.Service;

@Service
public class ProfilServiceImpl implements ProfilService {

    private final PunkteberechnungService punkteberechnungService;

    public ProfilServiceImpl(PunkteberechnungService punkteberechnungService) {
        this.punkteberechnungService = punkteberechnungService;
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
}
