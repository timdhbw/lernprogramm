package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Profil;

public interface ProfilRepository {

    Profil getProfilById(Integer profilId);
}
