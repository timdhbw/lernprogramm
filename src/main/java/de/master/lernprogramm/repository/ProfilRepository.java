package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Profil;

public interface ProfilRepository {

    Profil getProfilByProfilId(String profilId);
}
