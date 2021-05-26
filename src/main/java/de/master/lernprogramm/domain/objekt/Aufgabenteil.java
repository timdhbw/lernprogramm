package de.master.lernprogramm.domain.objekt;

import de.master.lernprogramm.domain.enumeration.AufgabenteiltypEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Aufgabenteil {

    private Integer laufenNr;
    private AufgabenteiltypEnum aufgabenteiltyp;
    private String text;
}
