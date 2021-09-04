package de.master.lernprogramm.domain.service;

import de.master.lernprogramm.domain.objekt.Profil;

public interface ProfilService {

    Profil getProfilById(String profilId);

    void setAufgabeVonProfilAbgeschlossen(Integer userId, Integer aufgabeId, Integer ergebnisUser);
}
