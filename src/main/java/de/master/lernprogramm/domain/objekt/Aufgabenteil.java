package de.master.lernprogramm.domain.objekt;

import de.master.lernprogramm.domain.enumeration.AufgabenteiltypEnum;
import lombok.Data;

@Data
public class Aufgabenteil {

    private Integer laufenNr;
    private AufgabenteiltypEnum aufgabenteiltyp;
}
