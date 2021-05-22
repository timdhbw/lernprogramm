package de.master.lernprogramm.domain.objekt;

import de.master.lernprogramm.domain.enums.AufgabenkategorieEnum;
import lombok.Data;

import java.util.List;

@Data
public class Aufgabe {

    private Integer aufgabeId;

    private String aufgabentitel;

    private Integer bewertung;

    private de.master.lernprogramm.web.api.dtos.ProfilUiDto autor;

    private AufgabenkategorieEnum kategorie;

    private List<de.master.lernprogramm.web.api.dtos.AufgabentagUiDto> aufgabentagList = null;

    private List<de.master.lernprogramm.web.api.dtos.AufgabenteilUiDto> aufgabenteilList = null;
}
