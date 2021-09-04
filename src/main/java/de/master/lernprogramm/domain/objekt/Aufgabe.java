package de.master.lernprogramm.domain.objekt;

import de.master.lernprogramm.domain.enums.AufgabenkategorieEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Aufgabe {

    private Integer aufgabeId;
    private String aufgabentitel;
    private Integer autorId;
    private AufgabenkategorieEnum kategorie;
    private List<Aufgabentag> aufgabentagList;
    private List<Aufgabenteil> aufgabenteilList;
    private List<AufgabenbewertungHistorie> aufgabenberwtungHistList;

    public void addAufgabenbewertungHistorie(AufgabenbewertungHistorie aufgabenbewertungHistorie) {
        if (aufgabenberwtungHistList == null) {
            aufgabenberwtungHistList = new ArrayList<>();
        }
        aufgabenberwtungHistList.add(aufgabenbewertungHistorie);
    }

    public Integer getBewertung() {
        if (aufgabenberwtungHistList == null || aufgabenberwtungHistList.isEmpty()) {
            return null;
        }
        return (int) aufgabenberwtungHistList.stream()
            .mapToInt(AufgabenbewertungHistorie::getBewertungsveraenderung)
            .average().orElse(0.0);
    }
}
