package de.master.lernprogramm.domain.objekt;

import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
public class Aufgabenhistorie {

    private LocalDate datum;
    private Integer bewertungsveraenderung;
    private Aufgabe aufgabe;

    public Integer getBewertungNachFormel() {
        long daysBetween = ChronoUnit.DAYS.between(datum, LocalDate.now());
        return (aufgabe.getBewertung() + bewertungsveraenderung) / (int) ((daysBetween < 1) ? 1 : daysBetween);
    }
}
