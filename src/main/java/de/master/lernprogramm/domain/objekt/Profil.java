package de.master.lernprogramm.domain.objekt;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Profil {

    private String profilId;
    private String vorname;
    private String nachname;
    private List<BewerteterAufgabentag> bewerteterAufgabentagList;
    private List<Aufgabenhistorie> aufgabenhistorieList;
    private List<Aufgabe> aufgabeList;

    private int punkte;
    private int rang;

    public void addAufgabenhistorie(Aufgabenhistorie newAufgabenhistorie) {
        if (aufgabenhistorieList == null) {
            aufgabenhistorieList = new ArrayList<>();
        }
        aufgabenhistorieList.add(newAufgabenhistorie);
    }

}
