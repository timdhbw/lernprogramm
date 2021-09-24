package de.master.lernprogramm.domain.objekt;

import de.master.lernprogramm.domain.enumeration.AufgabenteiltypEnum;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Builder
@Slf4j
public class Aufgabenteil {

    private Integer aufgabenteilId;
    private Integer laufenNr;
    private AufgabenteiltypEnum aufgabenteiltyp;
    private String text;
    private List<MultipleChoiceAntwort> multiplechoiceAntwortenList;

    /**
     * Wertet Aufgabenteil von 0 - 5 aus, wenn Auswertung nicht möglich wird -1 returned
     * @return auswertungErgebnis
     */
    public double werteAufgabenteilAus() {
        switch (aufgabenteiltyp) {
            case MULTIPLECHOICE:
                if (multiplechoiceAntwortenList.stream()
                    .anyMatch(multi -> multi.isChecked() != multi.isCheckedRichtig())) {
                    return 0;
                }
                return 5;
            default: return -1;
        }
    }
}
