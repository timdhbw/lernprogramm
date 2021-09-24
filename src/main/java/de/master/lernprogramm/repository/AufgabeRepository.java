package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Aufgabe;
import de.master.lernprogramm.domain.objekt.AufgabenbewertungHistorie;

import java.util.List;

public interface AufgabeRepository {

    Aufgabe saveAufgabe(Aufgabe aufgabeToSave);

    List<Integer> getAllPossibleAufgabenIds();

    Aufgabe getAufgabeById(Integer aufgabeId) ;

    void setAufgabeBewertung(Integer aufgabeId, AufgabenbewertungHistorie aufgabenbewertungHistorie);
}
