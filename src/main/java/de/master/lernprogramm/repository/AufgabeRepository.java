package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Aufgabe;

public interface AufgabeRepository {

    Aufgabe saveAufgabe(Aufgabe aufgabeToSave);

}
