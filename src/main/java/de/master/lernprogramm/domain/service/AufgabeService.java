package de.master.lernprogramm.domain.service;

import de.master.lernprogramm.domain.User;
import de.master.lernprogramm.domain.objekt.Aufgabe;

public interface AufgabeService {

    Aufgabe getAufgabeById(Integer aufgabeId);

    Aufgabe saveAufgabe(Aufgabe aufgabe);

    String getRandomNextAufgabeForUser(User user);

    void bewerteAufgabe(Integer aufgabeId, Integer aufgabeBewertung);

    double berechneErgebnis(Aufgabe toDomain);
}
