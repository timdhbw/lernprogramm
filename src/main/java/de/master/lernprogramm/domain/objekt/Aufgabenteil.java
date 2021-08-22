package de.master.lernprogramm.domain.objekt;

import de.master.lernprogramm.domain.enumeration.AufgabenteiltypEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Aufgabenteil {

    private Integer aufgabenteilId;
    private Integer laufenNr;
    private AufgabenteiltypEnum aufgabenteiltyp;
    private String text;
    private List<MultipleChoiceAntwort> multiplechoiceAntwortenList;
}
