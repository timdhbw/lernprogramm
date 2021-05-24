package de.master.lernprogramm.domain.objekt;

import de.master.lernprogramm.domain.enums.AufgabenkategorieEnum;
import lombok.Data;

import java.util.List;

@Data
public class Aufgabe {

    private Integer aufgabeId;
    private String aufgabentitel;
    private Integer bewertung;
    private Profil autor;
    private AufgabenkategorieEnum kategorie;
    private List<Aufgabentag> aufgabentagList;
    private List<Aufgabenteil> aufgabenteilList;
}
