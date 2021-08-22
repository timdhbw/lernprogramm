package de.master.lernprogramm.domain.objekt;

import lombok.Data;

@Data
public class MultipleChoiceAntwort {

    private boolean checked;
    private boolean checkedRichtig;
    private String label;

}
