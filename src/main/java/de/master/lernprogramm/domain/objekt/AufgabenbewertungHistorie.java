package de.master.lernprogramm.domain.objekt;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AufgabenbewertungHistorie implements Serializable {

    private LocalDate datum;
    private Integer bewertungsveraenderung;
}
