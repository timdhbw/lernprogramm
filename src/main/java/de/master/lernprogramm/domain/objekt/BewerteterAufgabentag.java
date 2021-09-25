package de.master.lernprogramm.domain.objekt;

import lombok.Data;

@Data
public class BewerteterAufgabentag {

    private double bewertung;
    private Aufgabentag aufgabentag;
    private boolean selected;

}
