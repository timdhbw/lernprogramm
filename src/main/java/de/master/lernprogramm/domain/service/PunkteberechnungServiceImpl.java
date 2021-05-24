package de.master.lernprogramm.domain.service;

import de.master.lernprogramm.domain.objekt.Aufgabenhistorie;
import de.master.lernprogramm.domain.objekt.Profil;
import org.springframework.stereotype.Service;

@Service
public class PunkteberechnungServiceImpl implements PunkteberechnungService {

    @Override
    public int berechnePunkteFuerProfil(Profil profil) {
        if (profil == null || profil.getAufgabenhistorieList() == null) {
            //TODO das jetzt nicht so geil, andere Exception?
            return 0;
        }
        return profil.getAufgabenhistorieList().stream()
            .mapToInt(Aufgabenhistorie::getBewertungNachFormel)
            .sum();
    }
}
