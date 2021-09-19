package de.master.lernprogramm.repository;

import de.master.lernprogramm.domain.objekt.Aufgabe;

import java.util.List;

public interface AufgabeRepository {

    Aufgabe saveAufgabe(Aufgabe aufgabeToSave);

    List<Integer> getAllPossibleAufgabenIds();

    Aufgabe getAufgabeById(Integer aufgabeId) ;
}
